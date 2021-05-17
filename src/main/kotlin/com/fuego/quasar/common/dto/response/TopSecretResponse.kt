package com.fuego.quasar.common.dto.response

import com.fuego.quasar.common.dto.Position

data class TopSecretResponse(
    val position: Position,
    val message: String
)