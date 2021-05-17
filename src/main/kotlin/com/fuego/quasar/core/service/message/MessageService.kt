package com.fuego.quasar.core.service.message

import com.fuego.quasar.common.exception.FuegoQuasarException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class MessageService {

    private val logger = LoggerFactory.getLogger(MessageService::class.java)

    fun getMessage(messages: List<List<String>>): String {

        if (messages.isEmpty())
            return "";

        val phrases = getDistinctPhrases(messages)
        val messagesCorrects = correctDifferenceMessages(messages.toMutableList(), phrases.size)

        val messageFinal = constructMessage(messagesCorrects)
        if (!isCorrectMessage(phrases, messageFinal)) {
            throw FuegoQuasarException(code = "404", status = HttpStatus.NOT_FOUND, message = "Bad message")
        }

        return messageFinal
    }

    private fun isCorrectMessage(phrases: List<String>, messageFinal: String): Boolean {
        val arrayMessage = messageFinal.split(' ')

        phrases.forEach { it ->
            if (!arrayMessage.contains(it)) {
                return false
            }
        }

        return true
    }

    private fun constructMessage(messages: MutableList<List<String>>): String {

        val message: MutableMap<Int, String> = HashMap()
        messages.forEach { list ->
            list.forEachIndexed { index, item ->
                if (item.isNotBlank() && !message.containsValue(item))
                    message.put(index, item)
            }
        }

        val orderesMessage = message.toSortedMap()

        return orderesMessage.values.joinToString(" ")
    }

    private fun correctDifferenceMessages(
        messages: MutableList<List<String>>,
        sizePhrases: Int
    ): MutableList<List<String>> {

        val messagesCorrects: MutableList<List<String>> = ArrayList()

        messages.forEachIndexed { index, value ->
            messagesCorrects.add(
                index, messages[index].subList(
                    (messages[index].size - sizePhrases),
                    messages[index].size
                )
            )
        }

        return messagesCorrects
    }

    private fun getDistinctPhrases(messages: List<List<String>>): List<String> {
        val result: MutableList<String> = ArrayList()

        for (item in messages) {
            result.addAll(item)
        }

        val uniques = result.distinct()

        return uniques.filter { item -> item.isNotEmpty() }
    }
}