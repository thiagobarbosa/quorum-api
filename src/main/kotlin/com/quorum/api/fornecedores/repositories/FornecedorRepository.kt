package com.quorum.api.fornecedores.repositories

import com.quorum.api.fornecedores.models.Fornecedor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FornecedorRepository : CrudRepository<Fornecedor, String> {
    fun findByCnpj(cnpj: String): Fornecedor?
}
