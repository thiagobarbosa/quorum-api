package com.quorum.api.repositories

import com.quorum.api.models.ItemReembolso
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ReembolsoRepository : CrudRepository<ItemReembolso, String>
