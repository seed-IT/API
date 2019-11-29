package eu.seed.it

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY
import com.fasterxml.jackson.annotation.PropertyAccessor.FIELD
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import eu.seed.it.configuration.Configuration
import eu.seed.it.configuration.ConfigurationImpl
import eu.seed.it.database.Database
import eu.seed.it.database.DatabaseImpl
import eu.seed.it.database.Sensor
import eu.seed.it.serialization.SensorDeserialiser
import eu.seed.it.serialization.SensorSerializer
import eu.seed.it.server.Server
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File


val kodein = Kodein {
    bind<Logger>() with singleton { LoggerFactory.getLogger("API")!! }

    bind<ObjectMapper>() with singleton {
        jacksonObjectMapper().findAndRegisterModules().also {
            it.setVisibility(FIELD, ANY)
            it.enable(INDENT_OUTPUT)
            val module = SimpleModule()
            module.addSerializer(Sensor::class.java, SensorSerializer())
            module.addDeserializer(Sensor::class.java, SensorDeserialiser())
            it.registerModule(module)
        }
    }

    bind<Database>() with singleton { DatabaseImpl() }

    bind<Server>() with singleton { Server() }

    bind<File>(tag = "configFile") with singleton {
        val cwd = System.getProperty("user.dir")
        File(cwd, "config.toml")
    }

    bind<Configuration>() with singleton { ConfigurationImpl() }
}


fun main() {
    val logger: Logger by kodein.instance()
    logger.info("Starting API service")

    val configuration: Configuration by kodein.instance()
    configuration.load()

    val server: Server by kodein.instance()
    server.serve()
}
