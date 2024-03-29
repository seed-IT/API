package eu.seed.it.server.modules

import eu.seed.it.server.ServerModule
import eu.seed.it.utils.message
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.inSet
import org.kodein.di.generic.singleton
import spark.kotlin.internalServerError
import spark.kotlin.notFound

private class ErrorModule : ServerModule {

    override fun run() {
        notFound {
            status(404)
            message("Not Found")
        }

        internalServerError {
            status(500)
            message("Internal Server Error")
        }

    }
}

val errorModule = Kodein.Module("error") {
    bind<ServerModule>().inSet() with singleton { ErrorModule() }
}