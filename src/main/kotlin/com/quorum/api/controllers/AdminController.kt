package com.quorum.api.controllers

import com.quorum.api.models.Despesa
import com.quorum.api.models.Fornecedor
import com.quorum.api.models.Vereador
import com.quorum.api.services.DespesaService
import com.quorum.api.services.FornecedorService
import com.quorum.api.services.VereadorService
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/admin")
class AdminController(
    private val vereadorService: VereadorService,
    private val fornecedorService: FornecedorService,
    private val despesaService: DespesaService
) {
    @PutMapping("/update/vereadores")
    fun updateVereadores(
        @RequestParam ano: String,
        @RequestParam mes: String
    ): List<Vereador> {
        return vereadorService.updateVereadores(ano, mes)
    }

    @PutMapping("/update/fornecedores")
    fun updateFornecedores(
        @RequestParam ano: String,
        @RequestParam mes: String
    ): List<Fornecedor> {
        return fornecedorService.updateFornecedores(ano, mes)
    }

    @PutMapping("/update/despesas")
    fun updateDespesas(
        @RequestParam ano: String,
        @RequestParam mes: String
    ): List<Despesa> {
        return despesaService.updateDespesas(ano, mes)
    }
}
