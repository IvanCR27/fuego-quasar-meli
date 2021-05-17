package com.fuego.quasar.common.constants

import com.fuego.quasar.common.dto.Position

object Constant {

    val KENOBI = Position(0.0, 0.0)
    val SKYWALKER = Position(100.0, 0.0)
    val SATO = Position(50.0, 100.0)

    const val SATELLITE_KENOBI = "kenobi"
    const val SATELLITE_SATO = "sato"
    const val SATELLITE_SKYWALKER = "skywalker"

    const val MIN_DISTANCES_SIZE = 3

    object PathVariable {
        const val SATELLITE_NAME = "satellite_name"
    }
}