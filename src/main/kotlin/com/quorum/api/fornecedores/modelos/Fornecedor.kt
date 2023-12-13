package com.quorum.api.fornecedores.modelos

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("Fornecedor")
data class Fornecedor(
    @Id
    val cnpj: String,
    val nome: String
)