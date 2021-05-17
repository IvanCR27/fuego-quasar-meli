package com.fuego.quasar.core.service

import com.fuego.quasar.app.Application
import com.fuego.quasar.common.exception.FuegoQuasarException
import com.fuego.quasar.core.service.location.LocationService
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [Application::class])
class LocationServiceTest {

    @Autowired
    private lateinit var locationService: LocationService

    @Test
    fun `get position ok`() {
        val positions = arrayListOf(100.0, 115.5, 142.7)
        val position = locationService.getLocation(positions)

        Assert.assertNotNull(position)
        Assert.assertTrue(position.x == 25.51885646020398)
        Assert.assertTrue(position.y == -62.272800971645125)
    }

    @Test
    fun `get position error`() {
        val positions = arrayListOf(100.0, 115.5)

        try {
            locationService.getLocation(positions)
        } catch (e: FuegoQuasarException) {
            Assert.assertEquals(e.message, "Must have at least 3 distances")
            Assert.assertEquals(e.code, "404")
        }
    }
}