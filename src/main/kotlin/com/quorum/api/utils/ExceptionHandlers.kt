package com.quorum.api.utils

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandlers {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleException(e: Exception): ErrorResponse {
        val status = HttpStatus.BAD_REQUEST.value().toString()
        val message = e.message ?: "Erro desconhecido"
        return ErrorResponse(status, message)
    }
}

data class ErrorResponse(val status: String, val message: String) {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    init {
        logger.info("Server error: $message")
    }
}
