package eu.seed.it

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import java.io.File


lateinit var serverConnection: Connection
lateinit var databaseConnection: Connection
lateinit var database: Database
lateinit var server: Server

var logger = LoggerFactory.getLogger("API")!!
val mapper = jacksonObjectMapper()

fun main() {
    logger.info("Starting API service")

    // TODO: find a better path
    val cwd = System.getProperty("user.dir")
    val configFile = File(cwd, "config.toml")
    Configuration.setConfigFile(configFile)
    Configuration.loadConfig()

    logger.info("Using server connection $serverConnection")
    logger.info("Using database connection $databaseConnection")

    mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
    mapper.enable(SerializationFeature.INDENT_OUTPUT)

    database = DummyDatabase(databaseConnection)

    server = Server(serverConnection, database)
    server.serve()
}
