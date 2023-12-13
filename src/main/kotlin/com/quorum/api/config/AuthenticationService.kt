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
    fun generateToken(): String {
        authenticationRepository.deleteAll()
        val token = UUID.randomUUID().toString()
        authenticationRepository.save(Authentication(token = token, expirationDate = ZonedDateTime.now().plusDays(365), role = "ADMIN"))
        return token
    }

    fun getAuthenticationByToken(token: String): Authentication {
        val authentication = authenticationRepository.findById(token).orElseThrow { Exception("Token $token not found") }
        if (authentication.expirationDate.isBefore(ZonedDateTime.now())) {
            throw Exception("Token $token is expired")
        }
        setSecurityContext(authentication)
        return authentication
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
