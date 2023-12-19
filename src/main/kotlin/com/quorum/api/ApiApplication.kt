package com.quorum.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity

@SpringBootApplication
@EnableGlobalMethodSecurity(jsr250Enabled = true)
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
