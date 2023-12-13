package com.quorum.api.reembolsos.servicos

import com.quorum.api.connectivity.makePostRequest
import com.quorum.api.connectivity.obterDebitoVereador
import com.quorum.api.despesas.modelos.Despesa
import com.quorum.api.despesas.servicos.DespesaService
import com.quorum.api.fornecedores.modelos.Fornecedor
import com.quorum.api.fornecedores.servicos.ServicoFornecedor
import com.quorum.api.reembolsos.modelos.ItemReembolso
import com.quorum.api.reembolsos.repositories.RepositorioReembolso
import com.quorum.api.vereadores.modelos.Vereador
import com.quorum.api.vereadores.servicos.ServicoVereador
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import parseXmlResponse

@Service
class ServicoReembolso(
    private val repositorioReembolso: RepositorioReembolso,
    private val despesaService: DespesaService,
    private val servicoVereador: ServicoVereador,
    private val servicoFornecedor: ServicoFornecedor
) {

    @Transactional
    fun apagarTodosReembolsos(): List<ItemReembolso> {
        val reembolsos = repositorioReembolso.findAll().toList()
        repositorioReembolso.deleteAll(reembolsos)
        return reembolsos
    }

    @Transactional
    fun atualizarReembolsos(ano: String, mes: String): List<ItemReembolso> {
        // Since the source API doesn't have a unique identifier for each item, we need to delete all
        repositorioReembolso.deleteAll()
        val url = obterDebitoVereador
        val xmlResponse = makePostRequest(url, ano, mes)
        val responseObj = parseXmlResponse(xmlResponse)

        return responseObj.items.map {
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