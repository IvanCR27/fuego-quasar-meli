package com.fuego.quasar.common.exception

import org.springframework.http.HttpStatus

class FuegoQuasarException(
    val code: String,
    override val message: String = "",
    val status: HttpStatus
) : RuntimeException()