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
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "2. Vereadores", description = "Endpoints para dados de vereadores")
@RequestMapping("/v1/vereadores")
class VereadorController(
    private val servicoVereador: ServicoVereador
) {
    @Operation(
        summary = "Listar vereadores",
        description = "Lista todos os vereadores.",
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
    @GetMapping
    fun obterTodosVereadores(): List<Vereador> {
        return servicoVereador.obterTodosVereadores()
    }

    @Operation(
        summary = "Obter vereador",
        description = "Obtem vereador por ID.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Sucesso",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = Vereador::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Acesso negado"
            ),
            ApiResponse(
                responseCode = "404",
                description = "Vereador não encontrado"
            )
        ],
        parameters = [
            Parameter(`in` = ParameterIn.HEADER, name = "token", description = "Token de acesso", required = true, schema = Schema(type = "string"))
        ]
    )
    @GetMapping("/{id}")
    fun obterVereadorPorId(
        @Parameter(description = "ID do vereador") @PathVariable id: String
    ): Vereador? {
        return servicoVereador.obterVereadorPorId(id) ?: throw Exception("Vereador não encontrado")
    }
}
