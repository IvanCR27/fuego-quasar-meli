package com.fuego.quasar.common.dto.request

data class TopSecretSplitRequest(
    val distance: Double,
    val message: List<String>
)