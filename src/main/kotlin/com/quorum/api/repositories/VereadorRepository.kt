package com.quorum.api.repositories

import com.quorum.api.models.Vereador
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface VereadorRepository : CrudRepository<Vereador, String>
