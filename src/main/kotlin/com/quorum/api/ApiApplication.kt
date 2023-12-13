package com.quorum.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@EnableGlobalMethodSecurity(jsr250Enabled = true)
@RestController
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
