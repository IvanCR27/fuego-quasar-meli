package com.fuego.quasar.model.entity

import javax.persistence.*

@Entity
@Table(name = "satellite")
data class SatelliteEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0L,
    var name: String,
    var distance: Double,
    var message: String
)