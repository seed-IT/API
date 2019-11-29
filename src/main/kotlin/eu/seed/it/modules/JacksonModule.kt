package eu.seed.it.modules

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import eu.seed.it.database.Sensor
import eu.seed.it.serialization.SensorDeserialiser
import eu.seed.it.serialization.SensorSerializer
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val jacksonModule = Kodein.Module(name = "Jackson") {
    bind<ObjectMapper>() with singleton {
        jacksonObjectMapper().findAndRegisterModules().also {
            it.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            it.enable(SerializationFeature.INDENT_OUTPUT)
            val module = SimpleModule()
            module.addSerializer(Sensor::class.java, SensorSerializer())
            module.addDeserializer(Sensor::class.java, SensorDeserialiser())
            it.registerModule(module)
        }
    }
}