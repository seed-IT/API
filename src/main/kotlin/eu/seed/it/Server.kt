package eu.seed.it

import eu.seed.it.Either.Left
import eu.seed.it.Either.Right
import eu.seed.it.RequestError.Invalid
import eu.seed.it.RequestError.NotFound
import eu.seed.it.RequestsSuccess.Created
import eu.seed.it.RequestsSuccess.OK
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
        when (val result = getHandler(database, req)) {
            is Right -> return@get result.value.toJson()
            is Left -> return@get handleErrors(res, result.value)
        }
    }
}

fun addPost(path: String, PostHandler: Post) {
    post(path) { req, res ->
        when (val result = postSeed(database, req)) {
            is Right -> return@post handleSuccess(res, result.value)
            is Left -> return@post handleErrors(res, result.value)
        }
    }
}

private fun Any.toJson(): String = mapper.writeValueAsString(this)

fun handleErrors(res: Response, error: RequestError) = when (error) {
    Invalid -> {
        res.status(400)
        message("400")
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