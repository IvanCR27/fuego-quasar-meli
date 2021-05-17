package com.fuego.quasar.core.handler

import com.fuego.quasar.common.exception.FuegoQuasarException
import com.fuego.quasar.common.exception.FuegoQuasarResponseException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(FuegoQuasarException::class)
    fun fuegoQuasarException(exception: FuegoQuasarException): ResponseEntity<FuegoQuasarResponseException> {
        val response = FuegoQuasarResponseException(exception.code, exception.message, exception.status.reasonPhrase)
        return ResponseEntity.status(exception.status).body(response)
    }

    @ExceptionHandler(Exception::class)
    fun exception(exception: Exception): ResponseEntity<FuegoQuasarResponseException> {
        val response = FuegoQuasarResponseException(
            code = "internal_server_error",
            message = exception.message ?: "An unexpected error has occurred",
            cause = exception.cause?.message
        )

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)
    }
}