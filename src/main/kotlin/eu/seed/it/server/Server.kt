package eu.seed.it.server

import eu.seed.it.Connection
import eu.seed.it.Either.Left
import eu.seed.it.Either.Right
import eu.seed.it.database.Database
import eu.seed.it.mapper
import eu.seed.it.server.RequestError.Invalid
import eu.seed.it.server.RequestError.NotFound
import eu.seed.it.server.RequestsSuccess.Created
import eu.seed.it.server.RequestsSuccess.OK
import eu.seed.it.toJson
import org.slf4j.LoggerFactory
import spark.Filter
import spark.Response
import spark.Spark.*


class Server(private val connection: Connection, private val database: Database) {
    private val logger = LoggerFactory.getLogger("Server")

    fun serve() {
        logger.info("Connecting to database")
        database.connect()

        logger.info("Listening on $connection")
        port(connection.port.value)

        before(Filter { req, res ->
            val url = req.url()
            if (url.endsWith('/')) {
                val newUrl = url.substring(0 until url.length - 1)
                logger.info("Redirecting from $url to $newUrl")
                res.redirect(newUrl)
            }
        })

        listOf(
                "/seeds" to getSeeds,
                "/seed/:id" to getSeed
        ).forEach { addGet(it.first, it.second) }

        listOf(
                "/seed" to postSeed
        ).forEach { addPost(it.first, it.second) }

        put("/seeds/:id") { req, res ->
            val updateSeed1 = updateSeed(req)
            when (updateSeed1) {
                is Left -> {
                    res.status(400)
                    return@put message("Invalid request")
                }
                is Right -> {
                    res.status(200)
                    return@put message("OK")
                }
            }

        }

        notFound { _, _ ->
            message("404")
        }

        after(Filter { _, res ->
            res.header("Content-Encoding", "gzip")
            res.type("application/json")
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


fun message(message: String): String {
    val objectNode = mapper.createObjectNode()
    objectNode.put("message", message)
    return objectNode.toString()
}