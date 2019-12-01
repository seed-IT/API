package eu.seed.it.server.modules

import eu.seed.it.server.ServerModule
import eu.seed.it.utils.message
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.inSet
import org.kodein.di.generic.singleton
import spark.kotlin.get

private class StatusModule : ServerModule {
    override fun run() {
        get("/status") {
            message("OK")
        }
    }
}

val statusModule = Kodein.Module("status") {
    bind<ServerModule>().inSet() with singleton { StatusModule() }
}