package com.quorum.api.fornecedores.controllers

import com.quorum.api.fornecedores.modelos.Fornecedor
import com.quorum.api.fornecedores.servicos.ServicoFornecedor
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "4. Fornecedores", description = "Dados de fornecedores")
@RequestMapping("/v1/fornecedores")
class FornecedorController(
    private val servicoFornecedor: ServicoFornecedor
) {
    @GetMapping
    fun getAllFornecedores(): List<Fornecedor> {
        return servicoFornecedor.obterTodosFornecedores()
    }

    @GetMapping("/{cnpj}")
    fun obterFornecedorPorCnpj(
        @PathVariable cnpj: String
    ): Fornecedor {
        return servicoFornecedor.obterFornecedorPorCnpj(cnpj) ?: throw Exception("Fornecedor não encontrado")
    }
}
