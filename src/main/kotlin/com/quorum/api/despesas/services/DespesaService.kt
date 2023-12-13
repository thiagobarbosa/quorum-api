package com.quorum.api.despesas.services

import com.quorum.api.connectivity.makePostRequest
import com.quorum.api.connectivity.obterDebitoVereador
import com.quorum.api.despesas.models.Despesa
import com.quorum.api.despesas.repositories.RepositorioDespesa
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import parseXmlResponse

@Service
class DespesaService(
    private val repositorioDespesa: RepositorioDespesa
) {

    @Transactional
    fun apagarTodasDespesas(): List<Despesa> {
        val despesas = repositorioDespesa.findAll().toList()
        repositorioDespesa.deleteAll(despesas)
        return despesas
    }

    fun obterTodasDespesas(): List<Despesa> {
        return repositorioDespesa.findAll().toList()
    }

    fun obterDespesaPorId(id: String): Despesa? {
        return repositorioDespesa.findById(id).orElse(null)
    }

    fun obterDespesaPorNome(nome: String): Despesa? {
        return repositorioDespesa.findByNomeCategoria(nome)
    }

    @Transactional
    fun atualizarDespesas(ano: String, mes: String): List<Despesa> {
        val url = obterDebitoVereador
        val xmlResponse = makePostRequest(url, ano, mes)
        val responseObj = parseXmlResponse(xmlResponse)

        val despesasDistintas = responseObj.items.distinctBy { it.nomeDespesa }
        return despesasDistintas.map {
            repositorioDespesa.findByNomeCategoria(it.nomeDespesa)
                ?: repositorioDespesa.save(Despesa(nomeCategoria = it.nomeDespesa))
        }
    }

    @Transactional
    fun criarDespesa(despesa: Despesa): Despesa {
        return repositorioDespesa.save(despesa)
    }
}
