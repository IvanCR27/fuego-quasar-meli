package com.fuego.quasar.common.dto.request

import com.fuego.quasar.common.dto.Satellite

data class TopSecretRequest(
    val satellites: List<Satellite>
)