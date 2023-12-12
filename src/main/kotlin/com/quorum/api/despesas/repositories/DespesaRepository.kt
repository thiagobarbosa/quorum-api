package com.quorum.api.despesas.repositories

import com.quorum.api.despesas.models.Despesa
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface DespesaRepository : CrudRepository<Despesa, String> {
    fun findByCategoryName(name: String): Despesa?
}
