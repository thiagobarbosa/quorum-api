package com.quorum.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
