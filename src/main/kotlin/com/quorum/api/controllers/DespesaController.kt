package com.quorum.api.controllers

import com.quorum.api.models.Despesa
import com.quorum.api.services.DespesaService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/despesas/")
class DespesaController(
    private val despesaService: DespesaService
) {
    @GetMapping
    fun getAllDespesas(): List<Despesa> {
        return despesaService.getAllDespesas()
    }

    @GetMapping("{name}")
    fun getVereadorById(
        @PathVariable name: String
    ): Despesa {
        return despesaService.getDespesaById(name) ?: throw Exception("Despesa não encontrado")
    }
}
