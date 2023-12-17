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
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RolesAllowed("ROLE_ADMIN")
@RestController
@Tag(name = "0. Administrador", description = "Endpoints restritos para administradores")
@RequestMapping("/v1/admin")
class AdminAuthController(
    private val servicoAutenticacao: ServicoAutenticacao
) {

    @Operation(
        summary = "Obter todas as autenticações do banco de dados",
        description = "Obter lista com todas as autenticações criadas",
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
            Parameter(`in` = ParameterIn.HEADER, name = "token", description = "Token de acesso", required = true, schema = Schema(type = "string"))
        ]
    )
    @GetMapping("/auth")
    fun obterTodasAutenticacoes(): List<Autenticacao> {
        return servicoAutenticacao.obterTodasAutenticacoes()
    }

    @Operation(
        summary = "Criar autenticacao",
        description = "Criar uma autenticação com permissão de administrador",
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
            Parameter(`in` = ParameterIn.HEADER, name = "token", description = "Token de acesso", required = true, schema = Schema(type = "string"))
        ]
    )
    @PostMapping("/auth/criar")
    fun criarTokenAdmin(): Autenticacao {
        return servicoAutenticacao.criarTokenAdmin()
    }

    @Operation(
        summary = "Apagar autenticacoes",
        description = "Apagar todas as autenticacoes de administradores",
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
            Parameter(`in` = ParameterIn.HEADER, name = "token", description = "Token de acesso", required = true, schema = Schema(type = "string"))
        ]
    )
    @DeleteMapping("/auth/apagar/todos")
    fun apagarTodasAutenticacoesAdmin(): List<Autenticacao> {
        return servicoAutenticacao.apagarTodasAutenticacoesAdmin()
    }

    @Operation(
        summary = "Apagar autenticacao",
        description = "Apagar uma autenticacao de administrador",
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
            Parameter(`in` = ParameterIn.PATH, name = "token", description = "Token de acesso", required = true, schema = Schema(type = "string"))
        ]
    )
    @DeleteMapping("/auth/apagar/{token}")
    fun apagarToken(
        @Parameter(description = "Token a ser apagado") @PathVariable token: String
    ): Autenticacao {
        return servicoAutenticacao.apagarToken(token)
    }
}
