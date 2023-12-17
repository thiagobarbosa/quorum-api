package com.quorum.api.vereadores.controllers

import com.quorum.api.vereadores.modelos.Vereador
import com.quorum.api.vereadores.servicos.ServicoVereador
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
@RequestMapping("/v1/admin/vereadores")
class VereadoresControllerAdmin(
    private val servicoVereador: ServicoVereador
) {

    @Operation(
        summary = "Atualizar vereadores",
        description = "Atualiza a lista de vereadores com base no ano informado",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Sucesso",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = Vereador::class))]
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
    fun atualizarVereadores(
        @Parameter(description = "Ano no banco de dados") @RequestParam ano: Int
    ): List<Vereador> {
        return servicoVereador.atualizarVereadores(ano)
    }

    @Operation(
        summary = "Apagar vereadores",
        description = "Apaga todos os vereadores do banco de dados",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Sucesso",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = Vereador::class))]
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
    fun apagarTodosVereadores(): List<Vereador> {
        return servicoVereador.apagarTodosVereadores()
    }
}
