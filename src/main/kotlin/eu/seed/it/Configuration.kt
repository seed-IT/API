package eu.seed.it

import com.electronwill.nightconfig.core.file.FileConfig
import org.slf4j.LoggerFactory
import java.io.File
import kotlin.system.exitProcess


object Configuration {
    private lateinit var configFile: File
    private val logger = LoggerFactory.getLogger("Configuration")

    fun setConfigFile(file: File) {
        configFile = file
    }

    fun loadConfig() {
        if (!configFile.exists()) logger.warn("${configFile.path} does not exist")
        else logger.info("Loading config from ${configFile.path}")

        val conf = FileConfig.of(configFile)
        conf.load()

        val serverAddress = conf.getOrElse("server.address", "127.0.0.1")
        val serverPort = conf.getIntOrElse("server.port", 4000)
        serverConnection = Connection(serverAddress, Port(serverPort))

        if (!serverConnection.port.validate()) {
            logger.error("Port out of range ${serverConnection.port}")
            exitProcess(1)
        }


        val paths = listOf(
                "database.address",
                "database.port",
                "database.name",
                "database.user",
                "database.password"
        )

        val missingConfigPaths = paths.filter { !conf.contains(it) }

        if (missingConfigPaths.isNotEmpty()) {
            missingConfigPaths.joinToString(", ")
                    .let { logger.error("Missing $it") }
            exitProcess(2)
        }

        databaseConnection = DatabaseConnection(
                address = conf["database.address"],
                port = Port(conf["database.port"]),
                name = conf["database.name"],
                user = conf["database.user"],
                password = conf["database.password"]
        )

        if (!databaseConnection.port.validate()) {
            logger.error("Port out of range ${databaseConnection.port}")
            exitProcess(1)
        }


    }
}

