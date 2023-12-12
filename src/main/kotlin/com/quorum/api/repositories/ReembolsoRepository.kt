package com.quorum.api.repositories

import com.quorum.api.models.ItemReembolso
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ReembolsoRepository : CrudRepository<ItemReembolso, String> {
    fun findAllByIdVereador(id: String): List<ItemReembolso>
    fun findAllByDespesa(despesa: String): List<ItemReembolso>
    fun findAllByCnpj(cnpj: String): List<ItemReembolso>
}
