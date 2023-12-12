package com.quorum.api.fornecedores.services

import com.quorum.api.connectivity.makePostRequest
import com.quorum.api.connectivity.obterDebitoVereador
import com.quorum.api.fornecedores.models.Fornecedor
import com.quorum.api.fornecedores.repositories.FornecedorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import parseXmlResponse

@Service
class FornecedorService(
    private val fornecedorRepository: FornecedorRepository
) {

    @Transactional
    fun deleteAllFornecedores() {
        fornecedorRepository.deleteAll()
    }

    fun getAllFornecedores(): List<Fornecedor> {
        return fornecedorRepository.findAll().toList()
    }

    fun getFornecedorByCnpj(id: String): Fornecedor? {
        return fornecedorRepository.findById(id).orElse(null)
    }

    @Transactional
    fun updateFornecedores(ano: String, mes: String): List<Fornecedor> {
        val url = obterDebitoVereador
        val xmlResponse = makePostRequest(url, ano, mes)
        val responseObj = parseXmlResponse(xmlResponse)

        val distinctFornecedores = responseObj.items.distinctBy { it.fornecedor }
        return distinctFornecedores.map {
            val formattedCnpj = it.cnpj.replace(".", "").replace("/", "").replace("-", "")
            fornecedorRepository.findByCnpj(formattedCnpj)
                ?: fornecedorRepository.save(
                    Fornecedor(
                        cnpj = formattedCnpj,
                        name = it.fornecedor
                    )
                )
        }
    }

    @Transactional
    fun createFornecedor(fornecedor: Fornecedor): Fornecedor {
        return fornecedorRepository.save(fornecedor)
    }
}
