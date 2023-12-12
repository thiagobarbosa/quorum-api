package com.quorum.api.services

import com.quorum.api.connectivity.makePostRequest
import com.quorum.api.connectivity.obterDebitoVereador
import com.quorum.api.models.Vereador
import com.quorum.api.repositories.VereadorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import parseXmlResponse

@Service
class VereadorService(
    private val vereadorRepository: VereadorRepository
) {

    fun getVereadorById(id: String): Vereador? {
        return vereadorRepository.findById(id).orElse(null)
    }

    fun getAllVereadores(): List<Vereador> {
        return vereadorRepository.findAll().toList()
    }

    @Transactional
    fun updateVereadores(ano: String, mes: String): List<Vereador> {
        val url = obterDebitoVereador
        val xmlResponse = makePostRequest(url, ano, mes)
        val responseObj = parseXmlResponse(xmlResponse)

        val distinctVereadores = responseObj.items.distinctBy { it.vereador }
        return distinctVereadores.map {
            vereadorRepository.save(
                Vereador(
                    id = it.chave,
                    name = it.vereador
                )
            )
        }
    }
}
