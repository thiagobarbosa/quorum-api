package com.quorum.api.models

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("Vereador")
data class Vereador(
    @Id
    val id: String,
    val name: String
)
