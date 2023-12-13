package com.quorum.api.despesas.repositories

import com.quorum.api.despesas.models.Despesa
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RepositorioDespesa : CrudRepository<Despesa, String> {
    fun findByNomeCategoria(nome: String): Despesa?
}
