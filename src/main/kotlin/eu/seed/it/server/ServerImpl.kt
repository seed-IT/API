package eu.seed.it.server

import eu.seed.it.configuration.Configuration
import eu.seed.it.database.Database
import eu.seed.it.kodein
import org.kodein.di.generic.instance
import org.slf4j.Logger
import spark.kotlin.port

class ServerImpl : Server {
    private val configuration: Configuration by kodein.instance()
    private val connection = configuration.serverConnection()
    private val database: Database by kodein.instance()
    private val logger: Logger by kodein.instance()

    override fun run() {
        database.connect()

        port(connection.port)

        val modules: Set<ServerModule> by kodein.instance()
        modules.forEach {
            it.run()
            logger.debug("Loading ${it::class.simpleName}")
        }
    }
}