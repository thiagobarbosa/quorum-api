package com.quorum.api.models

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash("Vereador")
data class Vereador(
    @Id
    val id: String,
    @Indexed
    val name: String
)
