package com.quorum.api.reembolsos.repositories

import com.quorum.api.reembolsos.modelos.ItemReembolso
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RepositorioReembolso : CrudRepository<ItemReembolso, String>, JpaSpecificationExecutor<ItemReembolso> {
    fun findAllByAno(ano: Int): List<ItemReembolso>

    fun findAllByAnoEqualsAndMesEquals(ano: Int, mes: Int): List<ItemReembolso>
}
