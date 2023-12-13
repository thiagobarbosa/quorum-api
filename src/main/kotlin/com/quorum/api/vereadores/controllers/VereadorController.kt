package com.quorum.api.vereadores.controllers

import com.quorum.api.vereadores.models.Vereador
import com.quorum.api.vereadores.services.VereadorService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RolesAllowed("ROLE_USER", "ROLE_PUBLIC")
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
