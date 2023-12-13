package com.quorum.api.redisflag

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.time.ZonedDateTime

@RedisHash("RedisCacheFlag")
data class RedisCacheFlag(
    @Id
    val id: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    val valor: ZonedDateTime?
)

enum class ChaveAtualizacao {
    ULTIMA_ATUALIZACAO_VEREADORES,
    ULTIMA_ATUALIZACAO_REEMBOLSOS,
    ULTIMA_ATUALIZACAO_FORNECEDORES,
    ULTIMA_ATUALIZACAO_DESPESAS
}
