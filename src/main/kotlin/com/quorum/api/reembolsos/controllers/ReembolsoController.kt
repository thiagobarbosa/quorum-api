package com.quorum.api.reembolsos.controllers

import com.quorum.api.reembolsos.modelos.ItemReembolso
import com.quorum.api.reembolsos.servicos.ServicoReembolso
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "5. Reembolsos", description = "Dados de reembolsos")
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
