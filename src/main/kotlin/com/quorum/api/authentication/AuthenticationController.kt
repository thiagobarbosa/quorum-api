package com.quorum.api.authentication

import com.quorum.api.authentication.models.Autenticacao
import com.quorum.api.authentication.servicos.ServicoAutenticacao
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RolesAllowed("ROLE_PUBLIC")
@RestController
@RequestMapping("/auth")
class AuthenticationController(
    private val servicoAutenticacao: ServicoAutenticacao
) {
    @PostMapping("/token/generate")
    fun generateUserToken(
        @RequestParam email: String
    ): Autenticacao {
        return servicoAutenticacao.criarTokenPrivado(email)
    }

    @PostMapping("/public-token/generate")
    fun generatePublicToken(): Autenticacao {
        return servicoAutenticacao.criarTokenPublico()
    }
}
