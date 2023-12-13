package com.quorum.api.vereadores.controllers

import com.quorum.api.vereadores.modelos.Vereador
import com.quorum.api.vereadores.servicos.ServicoVereador
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RolesAllowed("ROLE_ADMIN")
@RestController
@RequestMapping("/v1/admin/vereadores")
class VereadoresControllerAdmin(
    private val servicoVereador: ServicoVereador
) {

    @PutMapping("/atualizar")
    fun atualizarVereadores(
        @RequestParam ano: Int
    ): List<Vereador> {
        return servicoVereador.atualizarVereadores(ano)
    }

    @DeleteMapping("/apagar/todos")
    fun apagarTodosVereadores(): List<Vereador> {
        return servicoVereador.apagarTodosVereadores()
    }
}
