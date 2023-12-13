package com.quorum.api.despesas.controllers

import com.quorum.api.despesas.models.Despesa
import com.quorum.api.despesas.services.DespesaService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RolesAllowed("ROLE_ADMIN", "ROLE_USER", "ROLE_PUBLIC")
@RestController
@RequestMapping("/v1/despesas")
class DespesaController(
    private val despesaService: DespesaService
) {
    @GetMapping
    fun getAllDespesas(): List<Despesa> {
        return despesaService.obterTodasDespesas()
    }

    @GetMapping("/{id}")
    fun getVereadorById(
        @PathVariable id: String
    ): Despesa {
        return despesaService.obterDespesaPorId(id) ?: throw Exception("Despesa não encontrado")
    }
}
