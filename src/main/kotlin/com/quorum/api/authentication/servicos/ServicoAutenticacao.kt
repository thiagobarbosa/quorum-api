package com.quorum.api.authentication.servicos

import com.quorum.api.authentication.modelos.Autenticacao
import com.quorum.api.authentication.repositories.RepositorioAutenticacao
import com.quorum.api.messaging.ServicoEmail
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime
import java.util.UUID

@Service
class ServicoAutenticacao(
    private val repositorioAutenticacao: RepositorioAutenticacao,
    private val servicoEmail: ServicoEmail
) {

    // Temporarily setting the token as a role admin
    @Transactional
    fun criarTokenAdmin(): Autenticacao {
        val token = UUID.randomUUID().toString()
        return repositorioAutenticacao.save(Autenticacao(token = token, dataExpiracao = ZonedDateTime.now().plusDays(365), role = "ADMIN"))
    }

    fun obterTodasAutenticacoes(): List<Autenticacao> {
        return repositorioAutenticacao.findAll().toList()
    }

    @Transactional
    fun criarTokenPrivado(email: String): Autenticacao {
        if (email.isBlank()) throw Exception("Email nao pode ser vazio")

        val tokenExistente = repositorioAutenticacao.findByEmail(email)
        if (tokenExistente != null) {
            throw Exception(
                "Email $email já possui um token.\n" +
                    "Se precisar recuperar seu token nos contate pelo Github: https://github.com/thiagobarbosa/quorum-api"
            )
        }

        val token = UUID.randomUUID().toString()
        servicoEmail.sendEmail(email, "Your Quorum API token", token)
        val tokenSalvo = repositorioAutenticacao.save(Autenticacao(token = token, dataExpiracao = ZonedDateTime.now().plusDays(365), role = "USER", email = email))
        return Autenticacao(token = "token enviado por email", dataExpiracao = tokenSalvo.dataExpiracao, role = tokenSalvo.role, email = tokenSalvo.email)
    }

    @Transactional
    fun criarTokenPublico(): Autenticacao {
        val token = UUID.randomUUID().toString()
        return repositorioAutenticacao.save(Autenticacao(token = token, dataExpiracao = ZonedDateTime.now().plusDays(7), role = "PUBLIC"))
    }

    fun obterAutenticacaoPorToken(token: String): Autenticacao {
        val autenticacao = repositorioAutenticacao.findById(token).orElseThrow { Exception("Token $token nao encontrado.") }
        if (autenticacao.dataExpiracao.isBefore(ZonedDateTime.now())) {
            throw Exception("Token $token está expirado.")
        }
        definirSecurityContext(autenticacao)
        return autenticacao
    }

    @Transactional
    fun apagarToken(token: String): Autenticacao {
        val token = repositorioAutenticacao.findById(token).orElseThrow { Exception("Token $token nao encontrado") }
        repositorioAutenticacao.delete(token)
        return token
    }

    @Transactional
    fun apagarTodasAutenticacoesAdmin(): List<Autenticacao> {
        val tokensAdmin = repositorioAutenticacao.findAllByRole("ADMIN")
        repositorioAutenticacao.deleteAll(tokensAdmin)
        return tokensAdmin
    }

    fun definirSecurityContext(autenticacao: Autenticacao) {
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
            autenticacao.token,
            null,
            listOf(
                SimpleGrantedAuthority("ROLE_${autenticacao.role}")
            )
        )
    }
}
