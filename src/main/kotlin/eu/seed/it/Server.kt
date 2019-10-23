package eu.seed.it

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spark.Spark.*


class Server(private val connection: Connection, private val database: Database) {
    private val logger: Logger = LoggerFactory.getLogger("Server")
    private val mapper = ObjectMapper().registerModule(KotlinModule())

    init {
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
    }

    fun serve() {
        logger.info("Listening on $connection")
        port(connection.port)

        get("/seeds") { _, res ->
            res.type("application/json")

            val seeds = database.seeds()
            mapper.writeValueAsString(seeds)
        }

        notFound { _, res ->
            res.type("application/json")
            val objectNode = mapper.createObjectNode()
            objectNode.put("msg", "404")
            objectNode.toString()
        }

    }
}