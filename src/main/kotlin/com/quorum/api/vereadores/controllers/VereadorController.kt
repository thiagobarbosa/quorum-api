package com.quorum.api.vereadores.controllers

import com.quorum.api.vereadores.models.Vereador
import com.quorum.api.vereadores.services.ServicoVereador
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RolesAllowed("ROLE_ADMIN", "ROLE_USER", "ROLE_PUBLIC")
@RestController
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
