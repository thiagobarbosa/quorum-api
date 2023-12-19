package com.quorum.api.fornecedores.controllers

import com.quorum.api.fornecedores.modelos.Fornecedor
import com.quorum.api.fornecedores.servicos.ServicoFornecedor
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
@Tag(name = "4. Fornecedores", description = "Dados de fornecedores")
@RequestMapping("/v1/fornecedores")
class FornecedorController(
    private val servicoFornecedor: ServicoFornecedor
) {
    @Operation(
        summary = "Listar fornecedores",
        description = "Lista todos os fornecedores.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Sucesso",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = Fornecedor::class))]
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
    fun getAllFornecedores(
        @Parameter(description = "Pagina do resultado") @RequestParam page: Int?,
        @Parameter(description = "Tamanho da pagina do resultado") @RequestParam pageSize: Int?
    ): List<Fornecedor> {
        return servicoFornecedor.obterTodosFornecedores(page, pageSize)
    }

    @Operation(
        summary = "Obter fornecedor",
        description = "Obtem fornecedor pelo CNPJ.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Sucesso",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = Fornecedor::class))]
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
    @GetMapping("/{cnpj}")
    fun obterFornecedorPorCnpj(
        @Parameter(description = "Codigo do CNPJ (sem pontos ou tracos") @PathVariable cnpj: String
    ): Fornecedor {
        return servicoFornecedor.obterFornecedorPorCnpj(cnpj) ?: throw Exception("Fornecedor não encontrado")
    }
}
