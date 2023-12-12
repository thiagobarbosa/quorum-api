package com.quorum.api.controllers

import com.quorum.api.models.Vereador
import com.quorum.api.services.VereadorService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/vereadores/")
class VereadorController(
    private val vereadorService: VereadorService
) {
    @GetMapping
    fun getAllVereadores(): List<Vereador> {
        return vereadorService.getAllVereadores()
    }

    @GetMapping("/{id}")
    fun getVereadorById(
        @PathVariable id: String
    ): Vereador? {
        return vereadorService.getVereadorById(id) ?: throw Exception("Vereador não encontrado")
    }
}
