package com.quorum.api.despesas.repositories

import com.quorum.api.despesas.modelos.Despesa
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RepositorioDespesa : CrudRepository<Despesa, String> {
    fun findByNomeCategoria(nome: String): Despesa?
}
