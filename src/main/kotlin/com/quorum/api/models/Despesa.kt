package com.quorum.api.models

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("Despesa")
data class Despesa(
    @Id
    val name: String
)
