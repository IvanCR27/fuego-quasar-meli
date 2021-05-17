package com.fuego.quasar.core.service.communication

import com.fuego.quasar.common.dto.Satellite
import com.fuego.quasar.common.dto.request.TopSecretRequest
import com.fuego.quasar.common.dto.response.TopSecretResponse
import com.fuego.quasar.core.service.location.LocationService
import com.fuego.quasar.core.service.message.MessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class CommunicationService {

    @Autowired
    private lateinit var messageService: MessageService

    @Autowired
    private lateinit var locationService: LocationService

    fun getLocationShip(request: TopSecretRequest): ResponseEntity<TopSecretResponse> {

        val response = TopSecretResponse(
            position = locationService.getLocation(getPositions(request.satellites)),
            message = messageService.getMessage(getMessages(request.satellites))
        )

        return ResponseEntity.ok(response)
    }

    fun getPositions(satelites: List<Satellite>): List<Double> {
        val distances: MutableList<Double> = ArrayList()

        satelites.forEachIndexed { index, it -> distances.add(index, it.distance) }

        return distances
    }


    fun getMessages(satelites: List<Satellite>): List<List<String>> {
        val messages: MutableList<List<String>> = ArrayList()

        satelites.forEachIndexed { index, it -> messages.add(index, it.message) }

        return messages
    }
}