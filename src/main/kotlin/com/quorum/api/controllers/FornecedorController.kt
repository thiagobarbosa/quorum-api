package com.quorum.api.controllers

import com.quorum.api.models.Fornecedor
import com.quorum.api.services.FornecedorService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/fornecedores/")
class FornecedorController(
    private val fornecedorService: FornecedorService
) {
    @GetMapping
    fun getAllFornecedores(): List<Fornecedor> {
        return fornecedorService.getAllFornecedores()
    }

    @GetMapping("{cnpj}")
    fun getVereadorById(
        @PathVariable cnpj: String
    ): Fornecedor {
        return fornecedorService.getFornecedorByCNPJ(cnpj) ?: throw Exception("Fornecedor não encontrado")
    }
}
