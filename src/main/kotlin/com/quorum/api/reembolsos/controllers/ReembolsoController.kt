package com.quorum.api.reembolsos.controllers

import com.quorum.api.reembolsos.models.ItemReembolso
import com.quorum.api.reembolsos.services.ServicoReembolso
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RolesAllowed("ROLE_ADMIN", "ROLE_USER", "ROLE_PUBLIC")
@RestController
@RequestMapping("/v1/reembolsos")
class ReembolsoController(
    private val servicoReembolso: ServicoReembolso
) {

    @GetMapping
    fun getAllReembolsos(): List<ItemReembolso> {
        return servicoReembolso.obterTodosReembolsos()
    }

    @GetMapping("/vereador/{idVereador}")
    fun obterReembolsoPorIdVereador(
        @PathVariable idVereador: String
    ): List<ItemReembolso> {
        return servicoReembolso.obterReembolsoPorIdVereador(idVereador)
    }

    @GetMapping("/despesa/{id}")
    fun obterReembolsoPorIdDespesa(
        @PathVariable id: String
    ): List<ItemReembolso> {
        return servicoReembolso.obterReembolsoPorIdDespesa(id)
    }

    @GetMapping("/fornecedor/{cnpj}")
    fun getReembolsosByFornecedor(
        @PathVariable cnpj: String
    ): List<ItemReembolso> {
        return servicoReembolso.obterReembolsoPorCnpj(cnpj)
    }
}
