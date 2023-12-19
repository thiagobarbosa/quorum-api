package com.quorum.api.fornecedores.repositories

import com.quorum.api.fornecedores.modelos.Fornecedor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RepositorioFornecedor : CrudRepository<Fornecedor, String> {
    fun findAll(page: Pageable): Page<Fornecedor>
    fun findByCnpj(cnpj: String): Fornecedor?
}
