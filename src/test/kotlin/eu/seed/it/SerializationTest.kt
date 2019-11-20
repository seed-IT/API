package eu.seed.it

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import eu.seed.it.database.Sensor
import eu.seed.it.serialization.SensorDeserialiser
import eu.seed.it.serialization.SensorSerializer
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

class SerializationTest {
    val mapper: ObjectMapper = jacksonObjectMapper().findAndRegisterModules().apply {
        setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
        val module = SimpleModule()
        module.addSerializer(Sensor::class.java, SensorSerializer())
        module.addDeserializer(Sensor::class.java, SensorDeserialiser())
        registerModule(module)
    }


    @Test
    fun `Sensor serialization Test`() {
        val dt = LocalDateTime.of(2019, 1, 1, 1, 1)
        val sensor = Sensor(dt, 22.1f, 22.1f, 22.1f)
        val expected = """{"datetime":"2019-01-01T01:01:00","temperature":22.1,"humidity":22.1,"pressure":22.1}"""
        val json = mapper.writeValueAsString(sensor)
        assertEquals(expected, json)
    }
}