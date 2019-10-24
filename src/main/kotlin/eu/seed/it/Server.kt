package eu.seed.it

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.slf4j.LoggerFactory
import spark.Filter
import spark.Spark.*


class Server(private val connection: Connection, private val database: Database) {
    private val logger = LoggerFactory.getLogger("Server")
    private val mapper = ObjectMapper().registerModule(KotlinModule())

    init {
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
    }

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
                message("400", mapper)
            } else {
                val seed = database.seed(id)
                if (seed == null) {
                    res.status(404)
                    message("Seed not found", mapper)
                } else {
                    mapper.writeValueAsString(seed)
                }
            }
        }

        notFound { _, _ ->
            message("404", mapper)
        }

        after(Filter { _, res ->
            res.header("Content-Encoding", "gzip")
            res.type("application/json")
        })

    }
}

fun message(message: String, mapper: ObjectMapper): String {
    val objectNode = mapper.createObjectNode()
    objectNode.put("message", message)
    return objectNode.toString()
}