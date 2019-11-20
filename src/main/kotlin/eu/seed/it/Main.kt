package eu.seed.it

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import eu.seed.it.database.Database
import eu.seed.it.database.RealDatabase
import eu.seed.it.database.Sensor
import eu.seed.it.serialization.SensorDeserialiser
import eu.seed.it.serialization.SensorSerializer
import eu.seed.it.server.Server
import org.slf4j.LoggerFactory
import java.io.File


lateinit var serverConnection: Connection
lateinit var databaseConnection: DatabaseConnection
lateinit var database: Database
lateinit var server: Server

var logger = LoggerFactory.getLogger("API")!!

val mapper: ObjectMapper = jacksonObjectMapper().findAndRegisterModules().apply {
    setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
    enable(SerializationFeature.INDENT_OUTPUT)
    val module = SimpleModule()
    module.addSerializer(Sensor::class.java, SensorSerializer())
    module.addDeserializer(Sensor::class.java, SensorDeserialiser())
    registerModule(module)
}

fun main() {
    logger.info("Starting API service")

    // TODO: find a better path
    val cwd = System.getProperty("user.dir")
    val configFile = File(cwd, "config.toml")
    Configuration.setConfigFile(configFile)
    Configuration.loadConfig()

    logger.info("Using server connection $serverConnection")
    logger.info("Using database connection $databaseConnection")

    database = RealDatabase(databaseConnection)

    server = Server(serverConnection, database)
    server.serve()
}
