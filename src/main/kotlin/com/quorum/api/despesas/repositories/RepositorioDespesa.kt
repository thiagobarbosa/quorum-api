package com.quorum.api.despesas.repositories

import com.quorum.api.despesas.modelos.Despesa
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RepositorioDespesa : CrudRepository<Despesa, String> {
    fun findAll(page: Pageable): Page<Despesa>
    fun findByNomeCategoria(nome: String): Despesa?
}
