package com.quorum.api.reembolsos.repositories

import com.quorum.api.reembolsos.modelos.ItemReembolso
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RepositorioReembolso : CrudRepository<ItemReembolso, String> {
    fun findAll(page: Pageable): Page<ItemReembolso>
    fun findAllByIdVereador(id: String): List<ItemReembolso>
    fun findAllByCnpj(cnpj: String): List<ItemReembolso>
    fun findAllByIdDespesa(id: String): List<ItemReembolso>
    fun findAllByAno(ano: Int): List<ItemReembolso>
}
