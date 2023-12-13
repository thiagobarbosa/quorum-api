package com.quorum.api.admin

import com.quorum.api.vereadores.models.Vereador
import com.quorum.api.vereadores.services.ServicoVereador
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RolesAllowed("ROLE_ADMIN")
@RestController
@RequestMapping("/v1/admin/vereadores")
class AdminVereadoresController(
    private val servicoVereador: ServicoVereador
) {

    @PutMapping("/atualizar")
    fun atualizarVereadores(
        @RequestParam ano: String,
        @RequestParam mes: String
    ): List<Vereador> {
        return servicoVereador.atualizarVereadores(ano, mes)
    }

    @DeleteMapping("/apagar/todos")
    fun apagarTodosVereadores(): List<Vereador> {
        return servicoVereador.apagarTodosVereadores()
    }
}
