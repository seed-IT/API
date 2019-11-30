package eu.seed.it.modules

import eu.seed.it.server.Server
import eu.seed.it.server.ServerImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val serverModule = Kodein.Module(name = "Server") {
    bind<Server>() with singleton { ServerImpl() }
}