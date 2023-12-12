package com.quorum.api.services

import com.quorum.api.connectivity.makePostRequest
import com.quorum.api.connectivity.obterDebitoVereador
import com.quorum.api.models.Despesa
import com.quorum.api.repositories.DespesaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import parseXmlResponse

@Service
class DespesaService(
    private val despesaRepository: DespesaRepository
) {

    @Transactional
    fun deleteAllDespesas() {
        despesaRepository.deleteAll()
    }

    fun getAllDespesas(): List<Despesa> {
        return despesaRepository.findAll().toList()
    }

    fun getDespesaById(id: String): Despesa? {
        return despesaRepository.findById(id).orElse(null)
    }

    fun getDespesaByName(name: String): Despesa? {
        return despesaRepository.findByCategoryName(name)
    }

    @Transactional
    fun updateDespesas(ano: String, mes: String): List<Despesa> {
        val url = obterDebitoVereador
        val xmlResponse = makePostRequest(url, ano, mes)
        val responseObj = parseXmlResponse(xmlResponse)

        val distinctDespesas = responseObj.items.distinctBy { it.despesaName }
        return distinctDespesas.map {
            despesaRepository.findByCategoryName(it.despesaName)
                ?: despesaRepository.save(Despesa(categoryName = it.despesaName))
        }
    }

    @Transactional
    fun createDespesa(despesa: Despesa): Despesa {
        return despesaRepository.save(despesa)
    }
}
