package com.quorum.api.reembolsos.repositories

import com.quorum.api.reembolsos.models.ItemReembolso
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RepositorioReembolso : CrudRepository<ItemReembolso, String> {
    fun findAllByIdVereador(id: String): List<ItemReembolso>
    fun findAllByCnpj(cnpj: String): List<ItemReembolso>
    fun findAllByIdDespesa(id: String): List<ItemReembolso>
}
