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
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "5. Reembolsos", description = "Dados de reembolsos")
@RequestMapping("/v1/reembolsos")
class ReembolsoController(
    private val servicoReembolso: ServicoReembolso
) {

    @Operation(
        summary = "Listar reembolsos",
        description = "Lista todos os reembolsos de acordo com os filtros informados." +
            "\n\nNeste contexto, um 'reembolso' é uma solicitação feita por um vereador após uma despesa ser realizada." +
            "\n\nOs reembolsos são reportados mensalmente e contém informações como o ID do vereador, o CNPJ do fornecedor, o valor da despesa, etc.",
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
    @GetMapping
    fun getAllReembolsos(
        @Parameter(description = "ID do vereador") @RequestParam idVereador: String? = null,
        @Parameter(description = "ID da despesa") @RequestParam idDespesa: String? = null,
        @Parameter(description = "CNPJ do fornecedor") @RequestParam cnpj: String? = null,
        @Parameter(description = "Ano do reembolso") @RequestParam ano: Int? = null,
        @Parameter(description = "Mes do reembolso") @RequestParam mes: Int? = null,
        @Parameter(description = "Pagina do resultado") @RequestParam page: Int?,
        @Parameter(description = "Tamanho da pagina do resultado") @RequestParam pageSize: Int?
    ): List<Reembolso> {
        return servicoReembolso.obterTodosReembolsos(idVereador, idDespesa, cnpj, ano, mes, page, pageSize)
    }
}
