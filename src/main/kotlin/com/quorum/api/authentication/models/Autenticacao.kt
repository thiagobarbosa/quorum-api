package com.quorum.api.authentication.models

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.time.ZonedDateTime

@RedisHash("Authentication")
class Autenticacao(
    @Id
    val token: String,
    val dataExpiracao: ZonedDateTime,
    @Indexed
    val role: String,
    val email: String? = null
)

fun Autenticacao.obterLimiteRequisicao(): Int {
    return when (role) {
        "PUBLIC" -> 10
        "USER" -> 100
        "ADMIN" -> 1000
        else -> 0
    }
}
