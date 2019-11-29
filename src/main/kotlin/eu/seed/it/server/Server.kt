package eu.seed.it.server

import com.fasterxml.jackson.databind.ObjectMapper
import eu.seed.it.Either.Left
import eu.seed.it.Either.Right
import eu.seed.it.configuration.Configuration
import eu.seed.it.database.Database
import eu.seed.it.kodein
import eu.seed.it.server.RequestError.Invalid
import eu.seed.it.server.RequestError.NotFound
import eu.seed.it.server.RequestsSuccess.Created
import eu.seed.it.server.RequestsSuccess.OK
import eu.seed.it.toJson
import org.kodein.di.generic.instance
import org.slf4j.Logger
import spark.Filter
import spark.Response
import spark.Spark.*


class Server {
    private val logger: Logger by kodein.instance()
    private val configuration: Configuration by kodein.instance()
    private val connection = configuration.serverConnection()
    private val database: Database by kodein.instance()

    fun serve() {
        logger.info("Connecting to database")
        database.connect()

        logger.info("Listening on $connection")
        port(connection.port)

        before(Filter { req, res ->
            val url = req.url()
            if (url.endsWith('/')) {
                val newUrl = url.substring(0 until url.length - 1)
                logger.info("Redirecting from $url to $newUrl")
                res.redirect(newUrl)
            }
        })

        addPost("/sensor", postSensor)

        addGet("/sensor", getSensor)

        notFound { _, _ ->
            message("404")
        }

        after(Filter { _, res ->
            res.header("Content-Encoding", "gzip")

            // FIXME
            res.header("Access-Control-Allow-Origin", "*")

            res.type("application/json")
        })

        after(Filter { req, res ->
            val log = "${req.requestMethod()} ${req.url()} ${req.body()} -> ${res.status()} ${res.body()}"
            logger.info(log)
        })

    }
}


fun addGet(path: String, getHandler: Get<out Any>) {
    get(path) { req, res ->
        when (val result = getHandler(req)) {
            is Right -> return@get result.value.toJson()
            is Left -> return@get handleErrors(res, result.value)
        }
    }
}

fun addPost(path: String, postHandler: Post) {
    post(path) { req, res ->
        when (val result = postHandler(req)) {
            is Right -> return@post handleSuccess(res, result.value)
            is Left -> return@post handleErrors(res, result.value)
        }
    }
}

fun handleErrors(res: Response, error: RequestError) = when (error) {
    Invalid -> {
        res.status(400)
        message("Invalid request")
    }
    NotFound -> {
        res.status(404)
        message("Item not found")
    }
}

fun handleSuccess(res: Response, success: RequestsSuccess) = when (success) {
    OK -> {
        res.status(200)
        message("OK")
    }
    Created -> {
        res.status(201)
        message("Item created")
    }
}

private val mapper: ObjectMapper by kodein.instance()
fun message(message: String): String {
    val objectNode = mapper.createObjectNode()
    objectNode.put("message", message)
    return objectNode.toString()
}