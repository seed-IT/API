package eu.seed.it

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.IOException

sealed class Either<out A, out B> {
    class Left<A>(val value: A) : Either<A, Nothing>()
    class Right<B>(val value: B) : Either<Nothing, B>()
}

fun Any.toJson(): String = mapper.writeValueAsString(this)

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