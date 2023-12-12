package com.quorum.api.services

import com.quorum.api.connectivity.makePostRequest
import com.quorum.api.connectivity.obterDebitoVereador
import com.quorum.api.models.Fornecedor
import com.quorum.api.repositories.FornecedorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import parseXmlResponse

@Service
class FornecedorService(
    private val fornecedorRepository: FornecedorRepository
) {

    fun getAllFornecedores(): List<Fornecedor> {
        return fornecedorRepository.findAll().toList()
    }

    fun getFornecedorByCNPJ(id: String): Fornecedor? {
        return fornecedorRepository.findById(id).orElse(null)
    }

    @Transactional
    fun updateFornecedores(ano: String, mes: String): List<Fornecedor> {
        val url = obterDebitoVereador
        val xmlResponse = makePostRequest(url, ano, mes)
        val responseObj = parseXmlResponse(xmlResponse)

        val distinctFornecedores = responseObj.items.distinctBy { it.fornecedor }
        return distinctFornecedores.map {
            fornecedorRepository.save(
                Fornecedor(
                    cnpj = it.cnpj.replace(".", "").replace("/", "").replace("-", ""),
                    name = it.fornecedor
                )
            )
        }
    }
}
