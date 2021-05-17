package com.fuego.quasar.app.controller

import com.fuego.quasar.common.constants.Constant
import com.fuego.quasar.common.dto.request.TopSecretRequest
import com.fuego.quasar.common.dto.request.TopSecretSplitRequest
import com.fuego.quasar.common.dto.response.TopSecretResponse
import com.fuego.quasar.common.route.Route
import com.fuego.quasar.core.service.SatelliteService
import com.fuego.quasar.core.service.communication.CommunicationService
import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [Route.Communication.ROOT])
class CommunicationController {

    @Autowired
    private lateinit var service: CommunicationService

    @Autowired
    private lateinit var satelliteService: SatelliteService

    @PostMapping(
        value = [Route.Communication.TOP_SECRET],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun topSecret(@Valid @RequestBody request: TopSecretRequest): ResponseEntity<TopSecretResponse> {
        return service.getLocationShip(request);
    }

    @PostMapping(
        value = [Route.Communication.TOP_SECRET_SPLIT_PARAM],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun saveTopSecretSplit(
        @Valid @RequestBody request: TopSecretSplitRequest,
        @PathVariable(value = Constant.PathVariable.SATELLITE_NAME) satelliteName: String
    ): ResponseEntity<Void> {
        satelliteService.saveSatellite(satelliteName, request);
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping(
        value = [Route.Communication.TOP_SECRET_SPLIT],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getTopSecretSplit(): ResponseEntity<TopSecretResponse> {
        return satelliteService.getInformation();
    }
}