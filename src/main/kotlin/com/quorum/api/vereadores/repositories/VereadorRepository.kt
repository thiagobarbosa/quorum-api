package com.quorum.api.vereadores.repositories

import com.quorum.api.vereadores.models.Vereador
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface VereadorRepository : CrudRepository<Vereador, String> {
    fun findByName(name: String): Vereador?
}
