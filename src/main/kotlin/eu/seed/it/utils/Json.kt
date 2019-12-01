package eu.seed.it.utils

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import eu.seed.it.kodein
import eu.seed.it.utils.Either.Left
import eu.seed.it.utils.Either.Right
import org.kodein.di.generic.instance
import java.io.IOException


val mapper: ObjectMapper by kodein.instance()
fun Any.toJson(): String = mapper.writeValueAsString(this)

fun message(message: String): String {
    val objectNode = mapper.createObjectNode()
    objectNode.put("message", message)
    return objectNode.toString()
}

inline fun <reified T> String.toObject(): Either<JsonError, T> {
    return try {
        val value: T = mapper.readValue(this)
        Right(value)
    } catch (e: IOException) {
        Left(JsonError.IOException)
    } catch (e: JsonParseException) {
        Left(JsonError.JsonParseException)
    } catch (e: JsonMappingException) {
        Left(JsonError.JsonMappingException)
    }
}

enum class JsonError {
    IOException, JsonParseException, JsonMappingException
}