package com.quorum.api.authentication.controllers

import com.quorum.api.authentication.modelos.Autenticacao
import com.quorum.api.authentication.servicos.ServicoAutenticacao
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthenticationController(
    private val servicoAutenticacao: ServicoAutenticacao
) {
    @PostMapping("/privado/criar")
    fun generateUserToken(
        @RequestParam email: String
    ): Autenticacao {
        return servicoAutenticacao.criarTokenPrivado(email)
    }

    @PostMapping("/publico/criar")
    fun generatePublicToken(): Autenticacao {
        return servicoAutenticacao.criarTokenPublico()
    }
}
