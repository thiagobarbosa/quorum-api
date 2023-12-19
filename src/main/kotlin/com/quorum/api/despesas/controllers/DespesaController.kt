package com.quorum.api.despesas.controllers

import com.quorum.api.despesas.modelos.Despesa
import com.quorum.api.despesas.servicos.DespesaService
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "3. Despesas", description = "Dados de despesas")
@RequestMapping("/v1/despesas")
class DespesaController(
    private val despesaService: DespesaService
) {
    @Operation(
        summary = "Listar despesas",
        description = "Lista todas as despesas.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Sucesso",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = Despesa::class))]
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
    fun obterTodasDespesas(
        @Parameter(description = "Pagina do resultado") @RequestParam page: Int?,
        @Parameter(description = "Tamanho da pagina do resultado") @RequestParam pageSize: Int?
    ): List<Despesa> {
        return despesaService.obterTodasDespesas(page, pageSize)
    }

    @Operation(
        summary = "Obter despesa",
        description = "Obtem despesa por ID.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Sucesso",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = Despesa::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Acesso negado"
            ),
            ApiResponse(
                responseCode = "404",
                description = "Despesa não encontrada"
            )
        ],
        parameters = [
            Parameter(`in` = ParameterIn.HEADER, name = "token", description = "Token de acesso", required = true, schema = Schema(type = "string"))
        ]
    )
    @GetMapping("/{id}")
    fun obterDespesaPorId(
        @Parameter(description = "ID da despesa") @PathVariable id: String
    ): Despesa {
        return despesaService.obterDespesaPorId(id) ?: throw Exception("Despesa não encontrado")
    }
}
