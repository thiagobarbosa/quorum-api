package com.quorum.api.fornecedores.controllers

import com.quorum.api.fornecedores.models.Fornecedor
import com.quorum.api.fornecedores.services.ServicoFornecedor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RolesAllowed("ROLE_ADMIN", "ROLE_USER", "ROLE_PUBLIC")
@RestController
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
