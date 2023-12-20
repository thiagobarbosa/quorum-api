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
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RolesAllowed("ROLE_ADMIN")
@RestController
@Tag(name = "0. Administrador", description = "Endpoints restritos para administradores")
@RequestMapping("/v1/admin/despesas")
class DespesaControllerAdmin(
    private val despesaService: DespesaService
) {

    @Operation(
        summary = "Atualizar despesas",
        description = "Atualiza todas as despesas.",
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
    @PutMapping("/atualizar")
    fun atualizarDespesas(
        @Parameter(description = "Ano da despesa") @RequestParam ano: Int
    ): List<Despesa> {
        return despesaService.atualizarDespesas(ano)
    }

    @Operation(
        summary = "Apagar despesas",
        description = "Apaga todas as despesas.",
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
    @DeleteMapping("/apagar/todas")
    fun apagarTodasDespesas(): List<Despesa> {
        return despesaService.apagarTodasDespesas()
    }
}
