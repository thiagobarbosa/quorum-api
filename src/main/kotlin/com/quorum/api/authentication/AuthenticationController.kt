package com.quorum.api.authentication

import com.quorum.api.authentication.models.Authentication
import com.quorum.api.config.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
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
    @PostMapping("/token/generate")
    fun generateUserToken(
        @RequestParam email: String
    ): Authentication {
        return authenticationService.generateUserToken(email)
    }

    @PostMapping("/public-token/generate")
    fun generatePublicToken(): Authentication {
        return authenticationService.generatePublicToken()
    }
}
