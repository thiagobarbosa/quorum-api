package com.quorum.api.reembolsos.controllers

import com.quorum.api.reembolsos.modelos.Reembolso
import com.quorum.api.reembolsos.servicos.ServicoReembolso
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RolesAllowed("ROLE_ADMIN")
@RestController
@Tag(name = "0. Administrador", description = "Endpoints restritos para administradores")
@RequestMapping("/v1/admin/reembolsos")
class ReembolsoControllerAdmin(
    private val servicoReembolso: ServicoReembolso
) {

    @Operation(
        summary = "Atualizar reembolsos",
        description = "Atualiza a lista de reembolsos com base no ano informado",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Sucesso",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = Reembolso::class))]
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
    @PutMapping("/atualizar")
    fun atualizarReembolsos(
        @Parameter(description = "Ano do reembolso")
        @RequestParam(required = true)
        ano: Int,

        @Parameter(description = "Mes do reembolso")
        @RequestParam(required = false)
        mes: Int?
    ): List<Reembolso> {
        return servicoReembolso.atualizarReembolsos(ano, mes)
    }

    @Operation(
        summary = "Apagar reembolsos",
        description = "Remove todos os reembolsos do banco de dados",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Sucesso",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = Reembolso::class))]
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
    @DeleteMapping("/apagar/todos")
    fun apagarTodosReembolsos(): Boolean {
        return servicoReembolso.apagarTodosReembolsos()
    }
}
