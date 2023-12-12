package com.quorum.api.services

import com.quorum.api.connectivity.makePostRequest
import com.quorum.api.connectivity.obterDebitoVereador
import com.quorum.api.models.ItemReembolso
import com.quorum.api.repositories.ReembolsoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import parseXmlResponse

@Service
class ReembolsoService(
    private val reembolsoRepository: ReembolsoRepository
) {

    @Transactional
    fun updateReembolsos(ano: String, mes: String): List<ItemReembolso> {
        // Since the source API doesn't have a unique identifier for each item, we need to delete all
        reembolsoRepository.deleteAll()
        val url = obterDebitoVereador
        val xmlResponse = makePostRequest(url, ano, mes)
        val responseObj = parseXmlResponse(xmlResponse)

        return responseObj.items.map {
            reembolsoRepository.save(
                ItemReembolso(
                    idVereador = it.idVereador,
                    centroCustosId = it.centroCustosId,
                    departamento = it.departamento,
                    tipoDepartamento = it.tipoDepartamento,
                    vereador = it.vereador,
                    ano = it.ano,
                    mes = it.mes,
                    despesa = it.despesa,
                    cnpj = it.cnpj.replace(".", "").replace("/", "").replace("-", ""),
                    fornecedor = it.fornecedor,
                    valor = it.valor
                )
            )
        }
    }

    fun getAllReembolsos(): List<ItemReembolso> {
        return reembolsoRepository.findAll().toList()
    }

    fun getReembolsosByIdVereador(id: String): List<ItemReembolso> {
        return reembolsoRepository.findAllByIdVereador(id)
    }

    fun getReembolsosByDespesa(despesa: String): List<ItemReembolso> {
        return reembolsoRepository.findAllByDespesa(despesa)
    }

    fun getReembolsosByFornecedor(fornecedor: String): List<ItemReembolso> {
        return reembolsoRepository.findAllByCnpj(fornecedor)
    }
}
