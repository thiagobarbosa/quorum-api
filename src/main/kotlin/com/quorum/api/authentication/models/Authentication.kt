package com.quorum.api.authentication.models

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.time.ZonedDateTime

@RedisHash("Authentication")
class Authentication(
    @Id
    val token: String,
    val expirationDate: ZonedDateTime,
    val role: String
)
