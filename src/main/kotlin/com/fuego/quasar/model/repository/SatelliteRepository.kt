package com.fuego.quasar.model.repository

import com.fuego.quasar.model.entity.SatelliteEntity
import java.util.Optional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SatelliteRepository : JpaRepository<SatelliteEntity, Long> {

    fun findByName(name: String): Optional<SatelliteEntity>

    @Query("SELECT s FROM SatelliteEntity s WHERE s.name IN (:satellites)")
    fun findInformationSatellites(satellites: List<String>): List<SatelliteEntity>
}
