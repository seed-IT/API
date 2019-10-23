package eu.seed.it

import com.electronwill.nightconfig.core.file.FileConfig
import org.slf4j.LoggerFactory
import java.io.File


lateinit var serverConnection: Connection
lateinit var databaseConnection: Connection
lateinit var database: Database
lateinit var server: Server

var logger = LoggerFactory.getLogger("API")

fun main(args: Array<String>) {
    logger.info("Starting API service")

    loadConfig()

    database = DummyDatabase(databaseConnection)

    server = Server(serverConnection, database)
    server.serve()
}

fun loadConfig() {
    // TODO: find a better path
    val cwd = System.getProperty("user.dir")
    val configFile = File(cwd, "config.toml")

    if (!configFile.exists()) System.err.println("${configFile.path} does not exist")
    else println("Loading config from ${configFile.path}")

    val conf = FileConfig.of(configFile)
    conf.load()

    val serverAddress = conf.getOrElse("server.address", "127.0.0.1")
    val serverPort = conf.getIntOrElse("server.port", 4000)
    serverConnection = Connection(serverAddress, serverPort)

    val databaseAddress = conf.getOrElse("database.address", "127.0.0.1")
    val databasePort = conf.getIntOrElse("database.port", 3306)
    databaseConnection = Connection(databaseAddress, databasePort)

    println(serverConnection)
    println(databaseConnection)
}

data class Connection(val address: String, val port: Int) {
    override fun toString(): String {
        return "Connection('$address:$port')"
    }
}