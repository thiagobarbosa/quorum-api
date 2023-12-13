package com.quorum.api.admin

import com.quorum.api.authentication.models.Autenticacao
import com.quorum.api.authentication.servicos.ServicoAutenticacao
import com.quorum.api.despesas.models.Despesa
import com.quorum.api.despesas.services.DespesaService
import com.quorum.api.fornecedores.models.Fornecedor
import com.quorum.api.fornecedores.services.ServicoFornecedor
import com.quorum.api.reembolsos.models.ItemReembolso
import com.quorum.api.reembolsos.services.ServicoReembolso
import com.quorum.api.vereadores.models.Vereador
import com.quorum.api.vereadores.services.ServicoVereador
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RolesAllowed("ROLE_ADMIN")
@RestController
@RequestMapping("/v1/admin")
class AdminController(
    private val servicoVereador: ServicoVereador,
    private val servicoFornecedor: ServicoFornecedor,
    private val despesaService: DespesaService,
    private val servicoReembolso: ServicoReembolso,
    private val servicoAutenticacao: ServicoAutenticacao
) {

    @GetMapping("/auth")
    fun obterTodasAutenticacoes(): List<Autenticacao> {
        return servicoAutenticacao.obterTodasAutenticacoes()
    }

    @PutMapping("/auth/generate")
    fun criarTokenAdmin(): Autenticacao {
        return servicoAutenticacao.criarTokenAdmin()
    }

    @DeleteMapping("/auth/delete/all")
    fun apagarTodasAutenticacoesAdmin(): List<Autenticacao> {
        return servicoAutenticacao.apagarTodasAutenticacoesAdmin()
    }

    @DeleteMapping("/auth/token/delete/{token}")
    fun apagarToken(
        @PathVariable token: String
    ): Autenticacao {
        return servicoAutenticacao.apagarToken(token)
    }

    @PutMapping("/vereadores/update")
    fun atualizarVereadores(
        @RequestParam ano: String,
        @RequestParam mes: String
    ): List<Vereador> {
        return servicoVereador.atualizarVereadores(ano, mes)
    }

    @DeleteMapping("/vereadores/delete")
    fun apagarTodosVereadores() {
        servicoVereador.apagarTodosVereadores()
    }

    @PutMapping("/fornecedores/update")
    fun atualizarFornecedores(
        @RequestParam ano: String,
        @RequestParam mes: String
    ): List<Fornecedor> {
        return servicoFornecedor.atualizarFornecedores(ano, mes)
    }

    @DeleteMapping("/fornecedores/delete")
    fun apagarTodosFornecedores() {
        servicoFornecedor.apagarTodosFornecedores()
    }

    @PutMapping("/despesas/update")
    fun atualizarDespesas(
        @RequestParam ano: String,
        @RequestParam mes: String
    ): List<Despesa> {
        return despesaService.atualizarDespesas(ano, mes)
    }

    @DeleteMapping("/despesas/delete")
    fun apagarTodasDespesas(): List<Despesa> {
        return despesaService.apagarTodasDespesas()
    }

    @PutMapping("/reembolsos/update")
    fun atualizarReembolsos(
        @RequestParam ano: String,
        @RequestParam mes: String
    ): List<ItemReembolso> {
        return servicoReembolso.atualizarReembolsos(ano, mes)
    }

    @DeleteMapping("/reembolsos/delete")
    fun apagarTodosReembolsos(): List<ItemReembolso> {
        return servicoReembolso.apagarTodosReembolsos()
    }
}
