package com.quorum.api.authentication.controllers

import com.quorum.api.authentication.modelos.Autenticacao
import com.quorum.api.authentication.servicos.ServicoAutenticacao
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "1. Autenticacao", description = "Endpoints parar criacao de autenticacao")
@RequestMapping("/v1/auth")
class AuthenticationController(
    private val servicoAutenticacao: ServicoAutenticacao
) {
    @Operation(
        summary = "Criar token privado",
        description = "Cria um token privado para acesso aos endpoints restritos",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Sucesso",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = Autenticacao::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Acesso negado"
            )
        ],
        parameters = [
            Parameter(`in` = ParameterIn.QUERY, name = "email", description = "Email address", required = true, schema = Schema(type = "string"))
        ]
    )
    @PostMapping("/privado/criar")
    fun generateUserToken(
        @RequestParam email: String
    ): Autenticacao {
        return servicoAutenticacao.criarTokenPrivado(email)
    }

    @Operation(
        summary = "Criar token publico",
        description = "Cria um token publico para acesso aos endpoints restritos",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Sucesso",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = Autenticacao::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Acesso negado"
            )
        ]
    )
    @PostMapping("/publico/criar")
    fun generatePublicToken(): Autenticacao {
        return servicoAutenticacao.criarTokenPublico()
    }
}
