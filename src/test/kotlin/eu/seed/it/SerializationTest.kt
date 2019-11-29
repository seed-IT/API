package eu.seed.it

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import eu.seed.it.database.Sensor
import eu.seed.it.modules.jacksonModule
import org.junit.jupiter.api.Test
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import java.time.LocalDateTime
import kotlin.test.assertEquals

class SerializationTest {
    private val kodein = Kodein {
        import(jacksonModule)
    }

    private val mapper: ObjectMapper by kodein.instance()

    @Test
    fun `Sensor serialization Test`() {
        val dt = LocalDateTime.of(2019, 1, 1, 1, 1)
        val sensor = Sensor(dt, 22.1f, 22.1f, 22.1f)
        val expected = """
            {
              "datetime" : "2019-01-01T01:01:00",
              "temperature" : 22.1,
              "humidity" : 22.1,
              "pressure" : 22.1
            }
        """.trimIndent()
        val json = mapper.writeValueAsString(sensor)
        assertEquals(expected, json)
    }


    @Test
    fun `Sensor deserialization Test`() {
        val json = """{"datetime": "2019-11-14T10:11:59.378308+01:00", "temperature": 22.69, "humidity": 37.33, "pressure": 986.74}"""
        val sensor = mapper.readValue<Sensor>(json)
    }
}