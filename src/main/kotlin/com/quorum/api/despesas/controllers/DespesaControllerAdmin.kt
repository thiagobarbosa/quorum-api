package com.quorum.api.despesas.controllers

import com.quorum.api.despesas.modelos.Despesa
import com.quorum.api.despesas.servicos.DespesaService
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

    @PutMapping("/atualizar")
    fun atualizarDespesas(
        @RequestParam ano: Int
    ): List<Despesa> {
        return despesaService.atualizarDespesas(ano)
    }

    @DeleteMapping("/apagar/todas")
    fun apagarTodasDespesas(): List<Despesa> {
        return despesaService.apagarTodasDespesas()
    }
}
