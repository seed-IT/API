package eu.seed.it.server.modules

import eu.seed.it.kodein
import eu.seed.it.server.ServerModule
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.inSet
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.slf4j.Logger
import spark.kotlin.after

class LoggingModule : ServerModule {
    private val logger: Logger by kodein.instance()

    override fun run() {
        after {
            val log = "${request.requestMethod()} ${request.url()} ${request.body()} -> ${status()} ${response.body()}"
            logger.info(log)
        }
    }
}

val loggingModule = Kodein.Module("logging") {
    bind<ServerModule>().inSet() with singleton { LoggingModule() }
}