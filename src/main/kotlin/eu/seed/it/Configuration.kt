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
        serverConnection = Connection(serverAddress, serverPort)

        if (!serverConnection.validate()) {
            logger.error("Port out of range ${serverConnection.port}")
            exitProcess(1)
        }

        val databaseAddress = conf.getOrElse("database.address", "127.0.0.1")
        val databasePort = conf.getIntOrElse("database.port", 3306)
        databaseConnection = Connection(databaseAddress, databasePort)

        if (!databaseConnection.validate()) {
            logger.error("Port out of range ${databaseConnection.port}")
            exitProcess(1)
        }


    }
}

