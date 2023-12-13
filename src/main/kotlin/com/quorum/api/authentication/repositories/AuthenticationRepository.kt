package com.quorum.api.authentication.repositories

import com.quorum.api.authentication.models.Authentication
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthenticationRepository : CrudRepository<Authentication, String> {
    fun findAllByRole(role: String): List<Authentication>

    fun findByEmail(email: String): Authentication?
}
