package eu.seed.it

import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import spark.Filter
import spark.Spark.*


class Server(private val connection: Connection, private val database: Database) {
    private val logger = LoggerFactory.getLogger("Server")

    fun serve() {
        logger.info("Listening on $connection")
        port(connection.port)

        get("/seeds") { _, _ ->
            val seeds = database.seeds()
            mapper.writeValueAsString(seeds)
        }

        get("/seed/:id") { req, res ->
            val idParam = req.params(":id")
            val id = idParam.toIntOrNull()
            if (id == null) {
                res.status(400)
                message("400")
            } else {
                val seed = database.seed(id)
                if (seed == null) {
                    res.status(404)
                    message("Seed not found")
                } else {
                    mapper.writeValueAsString(seed)
                }
            }
        }

        post("/seed") { req, res ->
            val json = req.body()
            logger.info(json)
            when (val seed = seedFromJson(json)) {
                is Either.Left -> {
                    // TODO
                    res.status(400)
                    return@post message("Something happened..")
                }
                is Either.Right -> {
                    val success = database.addSeed(seed.value)
                    if (success) {
                        res.status(201)
                        return@post message("Inserted seed")
                    } else {
                        res.status(400)
                        // TODO: find what
                        return@post message("Something happened..")
                    }
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

fun seedFromJson(json: String): Either<Exception, Seed> {
    lateinit var seed: Seed
    try {
        seed = mapper.readValue(json)
    } catch (e: java.lang.Exception) {
        return Either.Left(e)
    }
    return Either.Right(seed)
}

fun message(message: String): String {
    val objectNode = mapper.createObjectNode()
    objectNode.put("message", message)
    return objectNode.toString()
}