package com.quorum.api.admin

import com.quorum.api.despesas.models.Despesa
import com.quorum.api.despesas.services.DespesaService
import com.quorum.api.fornecedores.models.Fornecedor
import com.quorum.api.fornecedores.services.FornecedorService
import com.quorum.api.reembolsos.models.ItemReembolso
import com.quorum.api.reembolsos.services.ReembolsoService
import com.quorum.api.vereadores.models.Vereador
import com.quorum.api.vereadores.services.VereadorService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/admin")
class AdminController(
    private val vereadorService: VereadorService,
    private val fornecedorService: FornecedorService,
    private val despesaService: DespesaService,
    private val reembolsoService: ReembolsoService
) {
    @PutMapping("/vereadores/update")
    fun updateVereadores(
        @RequestParam ano: String,
        @RequestParam mes: String
    ): List<Vereador> {
        return vereadorService.updateVereadores(ano, mes)
    }

    @DeleteMapping("/vereadores/delete")
    fun deleteAllVereadores() {
        vereadorService.deleteAllVereadores()
    }

    @PutMapping("/fornecedores/update")
    fun updateFornecedores(
        @RequestParam ano: String,
        @RequestParam mes: String
    ): List<Fornecedor> {
        return fornecedorService.updateFornecedores(ano, mes)
    }

    @DeleteMapping("/fornecedores/delete")
    fun deleteAllFornecedores() {
        fornecedorService.deleteAllFornecedores()
    }

    @PutMapping("/despesas/update")
    fun updateDespesas(
        @RequestParam ano: String,
        @RequestParam mes: String
    ): List<Despesa> {
        return despesaService.updateDespesas(ano, mes)
    }

    @DeleteMapping("/despesas/delete")
    fun deleteAllDespesas() {
        despesaService.deleteAllDespesas()
    }

    @PutMapping("/reembolsos/update")
    fun updateReembolsos(
        @RequestParam ano: String,
        @RequestParam mes: String
    ): List<ItemReembolso> {
        return reembolsoService.updateReembolsos(ano, mes)
    }

    @DeleteMapping("/reembolsos/delete")
    fun deleteAllReembolsos() {
        reembolsoService.deleteAllReembolsos()
    }
}
