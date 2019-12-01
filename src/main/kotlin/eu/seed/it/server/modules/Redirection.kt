package eu.seed.it.server.modules

import eu.seed.it.kodein
import eu.seed.it.server.ServerModule
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.inSet
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.slf4j.Logger
import spark.kotlin.before

private class RedirectionModule : ServerModule {
    private val logger: Logger by kodein.instance()

    override fun run() {
        before {
            val uri = uri()
            if (uri.endsWith('/')) {
                val newUrl = uri.substring(0 until uri.length - 1)
                logger.info("Redirecting from $uri to $newUrl")
                redirect(newUrl)
            }
        }
    }
}

val redirectionModule = Kodein.Module("redirection") {
    bind<ServerModule>().inSet() with singleton { RedirectionModule() }
}