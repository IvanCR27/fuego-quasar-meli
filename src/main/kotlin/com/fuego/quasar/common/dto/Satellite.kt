package com.fuego.quasar.common.dto


data class Satellite(
    val name: String,
    val distance: Double,
    val message: List<String>
)