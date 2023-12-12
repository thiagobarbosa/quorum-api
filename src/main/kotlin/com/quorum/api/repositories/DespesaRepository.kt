package com.quorum.api.repositories

import com.quorum.api.models.Despesa
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface DespesaRepository : CrudRepository<Despesa, String> {
    fun findByName(name: String): Despesa?
}
