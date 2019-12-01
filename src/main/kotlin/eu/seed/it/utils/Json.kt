package eu.seed.it.utils

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import eu.seed.it.kodein
import org.kodein.di.generic.instance
import java.io.IOException

sealed class Either<out A, out B> {
    class Left<A>(val value: A) : Either<A, Nothing>()
    class Right<B>(val value: B) : Either<Nothing, B>()
}

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
        Either.Right(value)
    } catch (e: IOException) {
        Either.Left(JsonError.IOException)
    } catch (e: JsonParseException) {
        Either.Left(JsonError.JsonParseException)
    } catch (e: JsonMappingException) {
        Either.Left(JsonError.JsonMappingException)
    }
}

enum class JsonError {
    IOException, JsonParseException, JsonMappingException
}