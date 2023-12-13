package com.quorum.api.reembolsos.controllers

import com.quorum.api.reembolsos.modelos.ItemReembolso
import com.quorum.api.reembolsos.servicos.ServicoReembolso
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RolesAllowed("ROLE_ADMIN")
@RestController
@RequestMapping("/v1/admin/reembolsos")
class ReembolsoControllerAdmin(
    private val servicoReembolso: ServicoReembolso
) {

    @PutMapping("/atualizar")
    fun atualizarReembolsos(
        @RequestParam ano: String,
        @RequestParam mes: String
    ): List<ItemReembolso> {
        return servicoReembolso.atualizarReembolsos(ano, mes)
    }

    @DeleteMapping("/apagar/todos")
    fun apagarTodosReembolsos(): List<ItemReembolso> {
        return servicoReembolso.apagarTodosReembolsos()
    }
}
