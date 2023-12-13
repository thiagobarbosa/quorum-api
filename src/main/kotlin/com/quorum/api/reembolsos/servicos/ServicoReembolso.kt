package com.quorum.api.reembolsos.servicos

import com.quorum.api.connectivity.makePostRequest
import com.quorum.api.connectivity.obterDebitoVereador
import com.quorum.api.despesas.modelos.Despesa
import com.quorum.api.despesas.servicos.DespesaService
import com.quorum.api.fornecedores.modelos.Fornecedor
import com.quorum.api.fornecedores.servicos.ServicoFornecedor
import com.quorum.api.redisflag.ChaveAtualizacao
import com.quorum.api.redisflag.RedisCacheFlag
import com.quorum.api.redisflag.RepositorioRedisCacheFlag
import com.quorum.api.reembolsos.modelos.ItemReembolso
import com.quorum.api.reembolsos.repositories.RepositorioReembolso
import com.quorum.api.utils.ANO_ATUAL
import com.quorum.api.utils.ANO_INICIO
import com.quorum.api.utils.MES_ATUAL
import com.quorum.api.vereadores.modelos.Vereador
import com.quorum.api.vereadores.servicos.ServicoVereador
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import parseXmlResponse
import java.time.ZonedDateTime

@Service
class ServicoReembolso(
    private val repositorioReembolso: RepositorioReembolso,
    private val despesaService: DespesaService,
    private val servicoVereador: ServicoVereador,
    private val servicoFornecedor: ServicoFornecedor,
    private val repositorioRedisCacheFlag: RepositorioRedisCacheFlag
) {

    @Transactional
    fun apagarTodosReembolsos(): List<ItemReembolso> {
        val reembolsos = repositorioReembolso.findAll().toList()
        repositorioReembolso.deleteAll(reembolsos)
        return reembolsos
    }

    @Transactional
    fun atualizarReembolsos(ano: Int): List<ItemReembolso> {
        if (ano > ANO_ATUAL || ano < ANO_INICIO) {
            throw Exception("Dados disponiveis somente a partir de $ANO_INICIO até $ANO_ATUAL")
        }

        // Já que a API fonte nao possui uma chave unica para reembolsos, apagamos todos os reembolsos do ano
        val reembolsosExistentesAno = repositorioReembolso.findAllByAno(ano)
        repositorioReembolso.deleteAll(reembolsosExistentesAno)

        val url = obterDebitoVereador

        val ultimoMes = if (ano == ANO_ATUAL) MES_ATUAL.minus(1) else 12
        val reembolsosAdicionados: MutableList<ItemReembolso> = mutableListOf()

        (1..ultimoMes).forEach { mes ->
            val xmlResponse = makePostRequest(url, ano, mes)
            val responseObj = parseXmlResponse(xmlResponse)

            repositorioReembolso.findById(responseObj.items.first().idVereador).ifPresent {
                throw Exception("Reembolsos já atualizados para o ano $ano")
            }

            reembolsosAdicionados.addAll(
                responseObj.items.map {
                    val cnpjFormatado = it.cnpj.replace(".", "").replace("/", "").replace("-", "")
                    val despesa = despesaService.obterDespesaPorNome(it.nomeDespesa) ?: despesaService.criarDespesa(Despesa(nomeCategoria = it.nomeDespesa))
                    val vereador = servicoVereador.obterVereadorPorNome(it.nomeVereador) ?: servicoVereador.criarVereador(Vereador(id = it.idVereador, nome = it.nomeVereador))
                    val fornecedor = servicoFornecedor.obterFornecedorPorCnpj(cnpjFormatado) ?: servicoFornecedor.criarFornecedor(
                        Fornecedor(cnpj = cnpjFormatado, nome = it.fornecedor)
                    )
                    repositorioReembolso.save(
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
                    )
                }
            )
        }

        repositorioRedisCacheFlag.save(
            RedisCacheFlag(
                id = ChaveAtualizacao.ULTIMA_ATUALIZACAO_REEMBOLSOS.name,
                valor = ZonedDateTime.now()
            )
        )

        return reembolsosAdicionados
    }

    fun obterTodosReembolsos(): List<ItemReembolso> {
        return repositorioReembolso.findAll().toList()
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
