package com.quorum.api.fornecedores.controllers

import com.quorum.api.fornecedores.models.Fornecedor
import com.quorum.api.fornecedores.services.ServicoFornecedor
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RolesAllowed("ROLE_ADMIN")
@RestController
@RequestMapping("/v1/admin/fornecedores")
class FornecedorControllerAdmin(
    private val servicoFornecedor: ServicoFornecedor
) {

    @PutMapping("/atualizar")
    fun atualizarFornecedores(
        @RequestParam ano: String,
        @RequestParam mes: String
    ): List<Fornecedor> {
        return servicoFornecedor.atualizarFornecedores(ano, mes)
    }

    @DeleteMapping("/apagar/todos")
    fun apagarTodosFornecedores(): List<Fornecedor> {
        return servicoFornecedor.apagarTodosFornecedores()
    }
}