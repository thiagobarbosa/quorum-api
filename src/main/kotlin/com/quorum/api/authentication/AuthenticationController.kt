package com.quorum.api.authentication

import com.quorum.api.config.AuthenticationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RolesAllowed("ROLE_PUBLIC")
@RestController
@RequestMapping("/auth")
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {
    @GetMapping("/token/generate")
    fun generateUserToken(
        @RequestParam email: String
    ): String {
        return authenticationService.generateUserToken(email)
    }

    @GetMapping("/public-token/generate")
    fun generatePublicToken(): String {
        return authenticationService.generatePublicToken()
    }
}
