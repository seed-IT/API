package eu.seed.it.server.modules

import eu.seed.it.server.ServerModule
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.inSet
import org.kodein.di.generic.singleton
import spark.kotlin.after

class HeaderModule : ServerModule {
    override fun run() {
        after {
            response.header("Content-Encoding", "gzip")
            response.type("application/json")
        }
    }
}

val headerModule = Kodein.Module("header") {
    bind<ServerModule>().inSet() with singleton { HeaderModule() }
}