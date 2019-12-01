package eu.seed.it.modules

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY
import com.fasterxml.jackson.annotation.PropertyAccessor.FIELD
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import eu.seed.it.database.schemas.Sensor
import eu.seed.it.serialization.SensorDeserializer
import eu.seed.it.serialization.SensorSerializer
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val jacksonModule = Kodein.Module(name = "Jackson") {
    bind<ObjectMapper>() with singleton {
        jacksonObjectMapper().findAndRegisterModules().also {
            it.setVisibility(FIELD, ANY)
            it.enable(INDENT_OUTPUT)
            val module = SimpleModule()
            module.addSerializer(Sensor::class.java, SensorSerializer())
            module.addDeserializer(Sensor::class.java, SensorDeserializer())
            it.registerModule(module)
        }
    }
}