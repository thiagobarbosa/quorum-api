package com.quorum.api.vereadores.controllers

import com.quorum.api.vereadores.modelos.Vereador
import com.quorum.api.vereadores.servicos.ServicoVereador
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
    @GetMapping
    fun obterTodosVereadores(): List<Vereador> {
        return servicoVereador.obterTodosVereadores()
    }

    @GetMapping("/{id}")
    fun obterVereadorPorId(
        @PathVariable id: String
    ): Vereador? {
        return servicoVereador.obterVereadorPorId(id) ?: throw Exception("Vereador não encontrado")
    }
}
