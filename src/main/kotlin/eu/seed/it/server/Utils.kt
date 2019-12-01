package eu.seed.it.server

import com.fasterxml.jackson.databind.ObjectMapper
import eu.seed.it.kodein
import org.kodein.di.generic.instance

private val mapper: ObjectMapper by kodein.instance()
fun message(message: String): String {
    val objectNode = mapper.createObjectNode()
    objectNode.put("message", message)
    return objectNode.toString()
}
