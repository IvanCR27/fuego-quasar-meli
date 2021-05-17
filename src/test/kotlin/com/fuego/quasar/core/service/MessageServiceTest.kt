package com.fuego.quasar.core.service

import com.fuego.quasar.app.Application
import com.fuego.quasar.common.exception.FuegoQuasarException
import com.fuego.quasar.core.service.message.MessageService
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [Application::class])
class MessageServiceTest {


    @Autowired
    private lateinit var service: MessageService

    @Test
    fun `get message ok`() {
        val message1 = listOf("", "este", "es", "un", "mensaje")
        val message2 = listOf("este", "", "un", "mensaje")
        val message3 = listOf("", "", "es", "", "mensaje")

        val message = service.getMessage(listOf(message1, message2, message3))

        Assert.assertTrue(message == "este es un mensaje")
    }

    @Test
    fun `get message error`() {
        val message1 = listOf("este", "", "", "mensaje", "")
        val message2 = listOf("", "es", "", "", "secreto")
        val message3 = listOf("este", "", "un", "", "", "")

        try {
            service.getMessage(listOf(message1, message2, message3))
        } catch (e: FuegoQuasarException) {
            Assert.assertEquals(e.message, "Bad message")
        }
    }

}