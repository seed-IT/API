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
            if (logger.isDebugEnabled) {
                val msg = "${request.requestMethod()} ${request.url()} ${request.body()} -> ${status()} ${response.body()}"
                logger.debug(msg)
            } else {
                val msg = "${request.requestMethod()} ${request.url()} -> ${status()}"
                logger.info(msg)
            }
        }
    }
}

val loggingModule = Kodein.Module("logging") {
    bind<ServerModule>().inSet() with singleton { LoggingModule() }
}