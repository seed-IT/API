package eu.seed.it.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import eu.seed.it.database.Sensor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class SensorSerializer : JsonSerializer<Sensor>() {
    private val FORMATTER: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME

    override fun serialize(value: Sensor, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeStartObject()
        gen.writeStringField("datetime", FORMATTER.format(value.datetime))
        gen.writeNumberField("temperature", value.temperature)
        gen.writeNumberField("humidity", value.humidity)
        gen.writeNumberField("pressure", value.pressure)
        gen.writeEndObject()
    }

    override fun handledType(): Class<Sensor> {
        return Sensor::class.java
    }

}

class SensorDeserialiser : JsonDeserializer<Sensor>() {
    private val FORMATTER: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Sensor {
        val node: JsonNode = p.codec.readTree(p)
        val datetimeString = node["datetime"].asText()
        val datetime: LocalDateTime = LocalDateTime.parse(datetimeString, FORMATTER)
        val temperature = node["temperature"].asDouble()
        val humidity = node["humidity"].asDouble()
        val pressure = node["pressure"].asDouble()
        return Sensor(datetime, temperature.toFloat(), humidity.toFloat(), pressure.toFloat())
    }

    override fun handledType(): Class<*> {
        return Sensor::class.java
    }
}