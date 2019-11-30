package eu.seed.it.server

import com.fasterxml.jackson.databind.ObjectMapper
import eu.seed.it.Either.Left
import eu.seed.it.Either.Right
import eu.seed.it.configuration.Configuration
import eu.seed.it.database.Database
import eu.seed.it.database.Sensor
import eu.seed.it.kodein
import eu.seed.it.toJson
import eu.seed.it.toObject
import org.kodein.di.generic.instance
import org.slf4j.Logger
import spark.kotlin.*

class ServerImpl : Server {
    private val logger: Logger by kodein.instance()
    private val configuration: Configuration by kodein.instance()
    private val connection = configuration.serverConnection()
    private val database: Database by kodein.instance()

    override fun run() {
        database.connect()

        logger.info("Listening on $connection")
        port(connection.port)

        before {
            val uri = uri()
            if (uri.endsWith('/')) {
                val newUrl = uri.substring(0 until uri.length - 1)
                logger.info("Redirecting from $uri to $newUrl")
                redirect(newUrl)
            }
        }

        get("/status") {
            message("OK")
        }

        get("/sensor") {
            response.header("Access-Control-Allow-Origin", "*")
            database.sensorData().toJson()
        }

        post("/sensor") {
            val sensorEither = request.body().toObject<Sensor>()

            if (sensorEither is Left) {
                response.status(400)
                return@post message("Bad Request")
            }

            sensorEither as Right
            val sensor = sensorEither.value

            database.addSensorData(sensor)

            status(201)
            message("Created")
        }

        notFound {
            status(404)
            message("Not Found")
        }

        internalServerError {
            status(500)
            message("Internal Server Error")
        }

        after {
            response.header("Content-Encoding", "gzip")
            response.type("application/json")
        }

        after {
            val log = "${request.requestMethod()} ${request.url()} ${request.body()} -> ${status()} ${response.body()}"
            logger.info(log)
        }

    }
}

private val mapper: ObjectMapper by kodein.instance()
private fun message(message: String): String {
    val objectNode = mapper.createObjectNode()
    objectNode.put("message", message)
    return objectNode.toString()
}
