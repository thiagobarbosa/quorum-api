package com.quorum.api.reembolsos.servicos

import com.quorum.api.connectivity.makePostRequest
import com.quorum.api.connectivity.obterDebitoVereador
import com.quorum.api.despesas.modelos.Despesa
import com.quorum.api.despesas.servicos.DespesaService
import com.quorum.api.fornecedores.modelos.Fornecedor
import com.quorum.api.fornecedores.servicos.ServicoFornecedor
import com.quorum.api.reembolsos.modelos.ItemReembolso
import com.quorum.api.reembolsos.repositories.RepositorioReembolso
import com.quorum.api.utils.ANO_ATUAL
import com.quorum.api.utils.ANO_INICIO
import com.quorum.api.utils.MES_ATUAL
import com.quorum.api.utils.defaultPageable
import com.quorum.api.vereadores.modelos.Vereador
import com.quorum.api.vereadores.servicos.ServicoVereador
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import parseXmlResponse
import kotlin.math.min

@Service
class ServicoReembolso(
    private val repositorioReembolso: RepositorioReembolso,
    private val despesaService: DespesaService,
    private val servicoVereador: ServicoVereador,
    private val servicoFornecedor: ServicoFornecedor
) {

    @Transactional
    fun apagarTodosReembolsos(): Boolean {
        repositorioReembolso.deleteAll()
        return true
    }

    @Transactional
    fun atualizarReembolsos(ano: Int, mes: Int? = null): List<ItemReembolso> {
        if (ano > ANO_ATUAL || ano < ANO_INICIO) {
            throw Exception("Dados disponiveis somente a partir de $ANO_INICIO até $ANO_ATUAL")
        }

        // calculate o ultimo mes disponivel para ser processado
        val ultimoMesDisponivel = if (ano == ANO_ATUAL) MES_ATUAL.minus(1) else 12

        mes?.let {
            if (it > ultimoMesDisponivel || it < 1) {
                throw Exception("Dados disponiveis somente a partir de $ANO_INICIO até $ANO_ATUAL no mes $ultimoMesDisponivel")
            }
        }

        // define o mes inicial a ser processado
        val mesInicial = mes ?: 1

        // limita o mes a ser processado ao ultimo mes disponivel
        val mesFinal = min(ultimoMesDisponivel, mes ?: ultimoMesDisponivel)

        // Já que a API fonte nao possui uma chave unica para reembolsos, apagamos todos os reembolsos do ano/mes
        val reembolsosExistentes = if (mes == null) repositorioReembolso.findAllByAno(ano) else repositorioReembolso.findAllByAnoEqualsAndMesEquals(ano, mesFinal)
        repositorioReembolso.deleteAll(reembolsosExistentes)

        val url = obterDebitoVereador

        val reembolsosAdicionados: MutableList<ItemReembolso> = mutableListOf()

        (mesInicial..mesFinal).forEach { mesProcessado ->
            val xmlResponse = makePostRequest(url, ano, mesProcessado)
            val responseObj = parseXmlResponse(xmlResponse)

            repositorioReembolso.findById(responseObj.items.first().idVereador).ifPresent {
                throw Exception("Reembolsos já atualizados para o ano $ano")
            }

            reembolsosAdicionados.addAll(
                responseObj.items.map {
                    val cnpjFormatado = it.cnpj.replace(".", "").replace("/", "").replace("-", "")
                    val despesa = despesaService.obterDespesaPorNome(it.nomeDespesa) ?: despesaService.criarDespesa(Despesa(nomeCategoria = it.nomeDespesa))
                    val vereador = servicoVereador.obterVereadorPorId(it.idVereador) ?: servicoVereador.criarVereador(Vereador(id = it.idVereador, nome = it.nomeVereador))
                    val fornecedor = servicoFornecedor.obterFornecedorPorCnpj(cnpjFormatado) ?: servicoFornecedor.criarFornecedor(
                        Fornecedor(cnpj = cnpjFormatado, nome = it.fornecedor)
                    )
                    ItemReembolso(
                        idVereador = vereador.id,
                        nomeVereador = vereador.nome,
                        idCentroCusto = it.idCentroCusto,
                        departamento = it.departamento,
                        tipoDepartamento = it.tipoDepartamento,
                        ano = it.ano,
                        mes = it.mes,
                        nomeDespesa = despesa.nomeCategoria,
                        idDespesa = despesa.id,
                        cnpj = fornecedor.cnpj,
                        fornecedor = fornecedor.nome,
                        valor = it.valor
                    )
                }
            )
        }

        repositorioReembolso.saveAll(reembolsosAdicionados)

        return reembolsosAdicionados
    }

    fun obterTodosReembolsos(idVereador: String?, idDespesa: String?, cnpj: String?, ano: Int?, mes: Int?, page: Int? = 0, pageSize: Int? = 100): List<ItemReembolso> {
        var reembolsosFiltrados = repositorioReembolso.findAll(defaultPageable(page, pageSize)).toList()

        if (ano != null) {
            reembolsosFiltrados = reembolsosFiltrados.filter { it.ano == ano }
        }

        if (mes != null) {
            reembolsosFiltrados = reembolsosFiltrados.filter { it.mes == mes }
        }

        if (idVereador != null) {
            reembolsosFiltrados = reembolsosFiltrados.filter { it.idVereador == idVereador }
        }

        if (idDespesa != null) {
            reembolsosFiltrados = reembolsosFiltrados.filter { it.idDespesa == idDespesa }
        }

        if (cnpj != null) {
            reembolsosFiltrados = reembolsosFiltrados.filter { it.cnpj == cnpj }
        }

        return reembolsosFiltrados
    }

    fun obterReembolsoPorIdVereador(id: String): List<ItemReembolso> {
        return repositorioReembolso.findAllByIdVereador(id)
    }

    fun obterReembolsoPorIdDespesa(idDespesa: String): List<ItemReembolso> {
        return repositorioReembolso.findAllByIdDespesa(idDespesa).toList()
    }

    fun obterReembolsoPorCnpj(cnpj: String): List<ItemReembolso> {
        return repositorioReembolso.findAllByCnpj(cnpj)
    }
}
