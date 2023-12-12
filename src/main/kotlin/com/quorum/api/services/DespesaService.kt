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

    fun getAllDespesas(): List<Despesa> {
        return despesaRepository.findAll().toList()
    }

    fun getDespesaByName(name: String): Despesa? {
        return despesaRepository.findById(name).orElse(null)
    }

    @Transactional
    fun updateDespesas(ano: String, mes: String): List<Despesa> {
        // Since the source API doesn't have a unique identifier for each item, we need to delete all
        despesaRepository.deleteAll()
        val url = obterDebitoVereador
        val xmlResponse = makePostRequest(url, ano, mes)
        val responseObj = parseXmlResponse(xmlResponse)

        val distinctDespesas = responseObj.items.distinctBy { it.despesa }
        return distinctDespesas.map {
            despesaRepository.save(
                Despesa(
                    name = it.despesa
                )
            )
        }
    }
}
