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
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RolesAllowed("ROLE_ADMIN")
@RestController
@Tag(name = "0. Administrador", description = "Endpoints restritos para administradores")
@RequestMapping("/v1/admin/fornecedores")
class FornecedorControllerAdmin(
    private val servicoFornecedor: ServicoFornecedor
) {

    @Operation(
        summary = "Atualizar fornecedores",
        description = "Atualiza a lista de fornecedores com base no ano informado",
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
    @PutMapping("/atualizar")
    fun atualizarFornecedores(
        @RequestParam ano: Int
    ): List<Fornecedor> {
        return servicoFornecedor.atualizarFornecedores(ano)
    }

    @Operation(
        summary = "Apagar fornecedores",
        description = "Apaga todos os fornecedores do banco de dados",
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
    @DeleteMapping("/apagar/todos")
    fun apagarTodosFornecedores(): List<Fornecedor> {
        return servicoFornecedor.apagarTodosFornecedores()
    }
}
