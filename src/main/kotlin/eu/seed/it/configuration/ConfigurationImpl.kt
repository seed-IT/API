package eu.seed.it.configuration

import com.electronwill.nightconfig.core.file.FileConfig
import eu.seed.it.Connection
import eu.seed.it.DatabaseConnection
import eu.seed.it.kodein
import org.kodein.di.generic.instance
import org.slf4j.Logger
import java.io.File


class ConfigurationImpl : Configuration {
    private val configFile: File by kodein.instance(tag = "configFile")
    private val logger: Logger by kodein.instance()

    private lateinit var conf: FileConfig

    override fun load() {
        if (!configFile.exists()) logger.warn("${configFile.path} does not exist")
        else logger.info("Loading config from ${configFile.path}")

        conf = FileConfig.of(configFile)
        conf.load()
    }

    override fun databaseConnection(): DatabaseConnection = DatabaseConnection(
            address = conf["database.address"],
            port = conf["database.port"],
            name = conf["database.name"],
            user = conf["database.user"],
            password = conf["database.password"]
    )

    override fun serverConnection(): Connection {
        val serverAddress = conf.getOrElse("server.address", "127.0.0.1")
        val serverPort = conf.getIntOrElse("server.port", 4000)
        return Connection(serverAddress, serverPort)
    }


}

