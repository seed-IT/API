package eu.seed.it

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spark.Spark.*

class Server(private val connection: Connection, private val database: Database) {
    private val logger: Logger = LoggerFactory.getLogger("Server")

    fun serve() {
        logger.info("Listening on $connection")
        port(connection.port)
        get("/seeds") { _, _ ->
            database.seeds().joinToString(", ")
        }

    }
}