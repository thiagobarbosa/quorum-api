package com.quorum.api.authentication.repositories

import com.quorum.api.authentication.modelos.Autenticacao
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RepositorioAutenticacao : CrudRepository<Autenticacao, String> {
    fun findAllByRole(role: String): List<Autenticacao>

    fun findByEmail(email: String): Autenticacao?
}
