package com.quorum.api.despesas.controllers

import com.quorum.api.despesas.modelos.Despesa
import com.quorum.api.despesas.servicos.DespesaService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "3. Despesas", description = "Dados de despesas")
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
