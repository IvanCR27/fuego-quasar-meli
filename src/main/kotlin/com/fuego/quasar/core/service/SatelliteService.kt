package com.fuego.quasar.core.service

import com.fuego.quasar.common.constants.Constant
import com.fuego.quasar.common.dto.Satellite
import com.fuego.quasar.common.dto.request.TopSecretRequest
import com.fuego.quasar.common.dto.request.TopSecretSplitRequest
import com.fuego.quasar.common.dto.response.TopSecretResponse
import com.fuego.quasar.common.exception.FuegoQuasarException
import com.fuego.quasar.core.service.communication.CommunicationService
import com.fuego.quasar.model.entity.SatelliteEntity
import com.fuego.quasar.model.repository.SatelliteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class SatelliteService {

    @Autowired
    private lateinit var repository: SatelliteRepository

    @Autowired
    private lateinit var communicationService: CommunicationService

    fun saveSatellite(satelliteName: String, request: TopSecretSplitRequest) {
        if (satelliteName.isEmpty()) {
            throw FuegoQuasarException(code = "400", status = HttpStatus.BAD_REQUEST, message = "Satellite empty")
        }

        val satelliteSearh = repository.findByName(satelliteName)

        if (satelliteName != null) {
            satelliteSearh.name = satelliteName
            satelliteSearh.distance = request.distance
            satelliteSearh.message = request.message.joinToString(separator = ",")

            repository.save(satelliteSearh)
        } else {
            val satelliteNew = SatelliteEntity(
                name = satelliteName,
                distance = request.distance,
                message = request.message.joinToString(separator = ",")
            )

            repository.save(satelliteNew)
        }
    }

    fun getInformation(): ResponseEntity<TopSecretResponse> {
        val satellites = arrayListOf(Constant.SATELLITE_KENOBI, Constant.SATELLITE_SATO, Constant.SATELLITE_SKYWALKER)

        val informationSatellites = repository.findInformationSatellites(satellites)

        if(informationSatellites.size < 3) {
            throw FuegoQuasarException(code = "404", status = HttpStatus.NOT_FOUND, message = "Information satellites incomplete")
        }

        val satellitesReq: MutableList<Satellite> = ArrayList()

        informationSatellites.forEach { satellite ->
            val item = Satellite(
                name = satellite.name,
                distance = satellite.distance,
                message = satellite.message.split(',')
            )

            satellitesReq.add(item)
        }

        val request = TopSecretRequest(
            satellites = satellitesReq
        )

        return communicationService.getLocationShip(request)
    }


}