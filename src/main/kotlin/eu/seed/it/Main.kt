package eu.seed.it

import eu.seed.it.configuration.Configuration
import eu.seed.it.modules.configurationModule
import eu.seed.it.modules.jacksonModule
import eu.seed.it.modules.mariaDBModule
import eu.seed.it.modules.serverModule
import eu.seed.it.server.Server
import eu.seed.it.server.modules.serverKodein
import org.kodein.di.Kodein
import org.kodein.di.description
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory


val kodein = Kodein {
    bind<Logger>() with singleton { LoggerFactory.getLogger("API")!! }

    import(jacksonModule)
    import(configurationModule)

    import(mariaDBModule)

    import(serverModule)
    extend(serverKodein)
}


fun main() {
    val logger: Logger by kodein.instance()
    logger.info("Starting API service")

    logger.debug(kodein.container.tree.bindings.description())

    val configuration: Configuration by kodein.instance()
    configuration.load()

    val server: Server by kodein.instance()
    server.run()
}
