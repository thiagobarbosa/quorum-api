package com.quorum.api.config

import com.quorum.api.authentication.models.Authentication
import com.quorum.api.authentication.repositories.AuthenticationRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime
import java.util.UUID

@Service
class AuthenticationService(
    val authenticationRepository: AuthenticationRepository
) {

    // Temporarily setting the token as a role admin
    @Transactional
    fun generateAdminToken(): Authentication {
        val token = UUID.randomUUID().toString()
        return authenticationRepository.save(Authentication(token = token, expirationDate = ZonedDateTime.now().plusDays(365), role = "ADMIN"))
    }

    fun getAllAuthentications(): List<Authentication> {
        return authenticationRepository.findAll().toList()
    }

    @Transactional
    fun generateUserToken(email: String): Authentication {
        if (email.isBlank()) throw Exception("Email cannot be blank")

        val existingToken = authenticationRepository.findByEmail(email)
        if (existingToken != null) {
            throw Exception(
                "Email $email already has a token. Generate a new one with a different email.\n" +
                    "If you need to recover your token please contact us via https://github.com/thiagobarbosa/quorum-api"
            )
        }

        val token = UUID.randomUUID().toString()
        return authenticationRepository.save(Authentication(token = token, expirationDate = ZonedDateTime.now().plusDays(365), role = "USER", email = email))
    }

    @Transactional
    fun generatePublicToken(): Authentication {
        val token = UUID.randomUUID().toString()
        return authenticationRepository.save(Authentication(token = token, expirationDate = ZonedDateTime.now().plusDays(7), role = "PUBLIC"))
    }

    fun getAuthenticationByToken(token: String): Authentication {
        val authentication = authenticationRepository.findById(token).orElseThrow { Exception("Token $token not found") }
        if (authentication.expirationDate.isBefore(ZonedDateTime.now())) {
            throw Exception("Token $token is expired")
        }
        setSecurityContext(authentication)
        return authentication
    }

    @Transactional
    fun deleteToken(token: String): Authentication {
        val adminAuth = authenticationRepository.findById(token).orElseThrow { Exception("Token $token not found") }
        authenticationRepository.delete(adminAuth)
        return adminAuth
    }

    @Transactional
    fun deleteAllAdminTokens(): List<Authentication> {
        val adminTokens = authenticationRepository.findAllByRole("ADMIN")
        authenticationRepository.deleteAll(adminTokens)
        return adminTokens
    }

    fun setSecurityContext(authentication: Authentication) {
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
            authentication.token,
            null,
            listOf(
                SimpleGrantedAuthority("ROLE_${authentication.role}")
            )
        )
    }
}
