package com.fuego.quasar.core.app.controller

import com.fuego.quasar.app.Application
import com.fuego.quasar.common.dto.Satellite
import com.fuego.quasar.common.dto.request.TopSecretRequest
import com.fuego.quasar.common.dto.request.TopSecretSplitRequest
import com.fuego.quasar.common.route.Route
import com.google.gson.Gson
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest(classes = [Application::class])
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommunicationControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun `top secret success`() {
        val url = Route.Communication.ROOT + Route.Communication.TOP_SECRET
        val request = MockMvcRequestBuilders
            .post(url)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(createRequestSuccess())
            .servletPath(url)
        mvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `top secret message error`() {
        val url = Route.Communication.ROOT + Route.Communication.TOP_SECRET
        val request = MockMvcRequestBuilders
            .post(url)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(createRequestError())
            .servletPath(url)
        mvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `top secret split success`() {
        val url =
            Route.Communication.ROOT + Route.Communication.TOP_SECRET_SPLIT_PARAM.replace("{satellite_name}", "kenobi")
        val request = MockMvcRequestBuilders
            .post(url)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(createRequestSplitSuccess())
            .servletPath(url)
        mvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk)
    }

    private fun createRequestSplitSuccess(): String {
        val request = TopSecretSplitRequest(
            distance = 25.0,
            message = arrayListOf("este", "", "", "mensaje", "")
        )

        return Gson().toJson(request)
    }

    private fun createRequestSuccess(): String {
        val satelite1 = Satellite(
            "kenobi", 100.0, arrayListOf("este", "", "", "mensaje", "")
        )

        val satelite2 = Satellite(
            "skywalker", 115.5, arrayListOf("", "es", "", "", "secreto")
        )

        val satelite3 = Satellite(
            "skywalker", 115.5, arrayListOf("este", "", "un", "", "")
        )

        val request = TopSecretRequest(arrayListOf(satelite1, satelite2, satelite3))
        val gson = Gson()

        return gson.toJson(request)
    }

    private fun createRequestError(): String {
        val satelite1 = Satellite(
            "kenobi", 100.0, arrayListOf("este", "", "", "mensaje", "")
        )

        val satelite2 = Satellite(
            "skywalker", 115.5, arrayListOf("", "es", "", "", "secreto")
        )

        val satelite3 = Satellite(
            "skywalker", 115.5, arrayListOf("este", "", "un", "", "", "")
        )

        val request = TopSecretRequest(arrayListOf(satelite1, satelite2, satelite3))
        val gson = Gson()

        return gson.toJson(request)
    }
}