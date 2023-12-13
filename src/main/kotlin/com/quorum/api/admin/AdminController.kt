package com.quorum.api.admin

import com.quorum.api.authentication.models.Autenticacao
import com.quorum.api.authentication.servicos.ServicoAutenticacao
import com.quorum.api.reembolsos.services.ServicoReembolso
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RolesAllowed("ROLE_ADMIN")
@RestController
@RequestMapping("/v1/admin")
class AdminController(
    private val servicoReembolso: ServicoReembolso,
    private val servicoAutenticacao: ServicoAutenticacao
) {

    @GetMapping("/auth")
    fun obterTodasAutenticacoes(): List<Autenticacao> {
        return servicoAutenticacao.obterTodasAutenticacoes()
    }

    @PostMapping("/auth/criar")
    fun criarTokenAdmin(): Autenticacao {
        return servicoAutenticacao.criarTokenAdmin()
    }

    @DeleteMapping("/auth/apagar/todos")
    fun apagarTodasAutenticacoesAdmin(): List<Autenticacao> {
        return servicoAutenticacao.apagarTodasAutenticacoesAdmin()
    }

    @DeleteMapping("/auth/apagar/{token}")
    fun apagarToken(
        @PathVariable token: String
    ): Autenticacao {
        return servicoAutenticacao.apagarToken(token)
    }
}
