package com.quorum.api.vereadores.repositorios

import com.quorum.api.vereadores.modelos.Vereador
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RepositorioVereador : CrudRepository<Vereador, String> {
    fun findAll(page: Pageable): Page<Vereador>
    fun findByNome(nome: String): Vereador?
}
