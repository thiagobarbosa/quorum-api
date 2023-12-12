package com.quorum.api.models

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.util.UUID

@RedisHash("Despesa")
data class Despesa(
    @Id
    val id: String = UUID.randomUUID().toString(),
    @Indexed
    val name: String
)
