package com.fuego.quasar.common.exception

data class FuegoQuasarResponseException(
    val code: String,
    val message: String = "An unexpected error has occurred",
    val cause: String? = null
)