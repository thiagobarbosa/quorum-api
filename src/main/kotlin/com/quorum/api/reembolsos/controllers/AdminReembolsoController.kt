package com.quorum.api.reembolsos.controllers

import com.quorum.api.authentication.servicos.ServicoAutenticacao
import com.quorum.api.reembolsos.models.ItemReembolso
import com.quorum.api.reembolsos.services.ServicoReembolso
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RolesAllowed("ROLE_ADMIN")
@RestController
@RequestMapping("/v1/admin/reembolsos")
class AdminReembolsoController(
    private val servicoReembolso: ServicoReembolso,
    private val servicoAutenticacao: ServicoAutenticacao
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
