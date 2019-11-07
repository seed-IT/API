package eu.seed.it

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import eu.seed.it.database.SensorSerializable
import org.junit.jupiter.api.Test


class ParsingTest {

    val mapper: ObjectMapper = jacksonObjectMapper().findAndRegisterModules().apply {
        setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
    }

    @Test
    fun `parse json sensor test`() {
        val json = """
        {"datetime": "2019-11-14T10:11:59.378308+01:00", "temperature": 22.69, "humidity": 37.33, "pressure": 986.74}
        """.trimIndent()

        val ss: SensorSerializable = mapper.readValue<SensorSerializable>(json)
        ss.toSensor()
    }

}
