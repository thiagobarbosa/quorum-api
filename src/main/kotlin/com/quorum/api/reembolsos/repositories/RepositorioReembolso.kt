package com.quorum.api.reembolsos.repositories

import com.quorum.api.reembolsos.modelos.Reembolso
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RepositorioReembolso : CrudRepository<Reembolso, String>, JpaSpecificationExecutor<Reembolso> {
    fun findAllByAno(ano: Int): List<Reembolso>

    fun findAllByAnoEqualsAndMesEquals(ano: Int, mes: Int): List<Reembolso>
}
