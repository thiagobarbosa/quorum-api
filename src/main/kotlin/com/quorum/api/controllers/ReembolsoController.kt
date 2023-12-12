package com.quorum.api.controllers

import com.quorum.api.models.ItemReembolso
import com.quorum.api.services.ReembolsoService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/reembolsos/")
class ReembolsoController(
    private val reembolsoService: ReembolsoService
) {

    @GetMapping
    fun getAllReembolsos(): List<ItemReembolso> {
        return reembolsoService.getAllReembolsos()
    }

    @GetMapping("vereador/{idVereador}")
    fun getReembolsosByVereadorId(
        @PathVariable idVereador: String
    ): List<ItemReembolso> {
        return reembolsoService.getReembolsosByIdVereador(idVereador)
    }

    @GetMapping("despesa/{id}")
    fun getReembolsosByDespesaId(
        @PathVariable id: String
    ): List<ItemReembolso> {
        return reembolsoService.getReembolsosByDespesaId(id)
    }

    @GetMapping("fornecedor/{cnpj}")
    fun getReembolsosByFornecedor(
        @PathVariable cnpj: String
    ): List<ItemReembolso> {
        return reembolsoService.getReembolsosByFornecedor(cnpj)
    }
}
