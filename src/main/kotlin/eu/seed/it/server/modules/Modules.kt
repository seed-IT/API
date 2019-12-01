package eu.seed.it.server.modules

import eu.seed.it.server.ServerModule
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.setBinding

val serverKodein = Kodein {
    bind() from setBinding<ServerModule>()
    import(errorModule)
    import(headerModule)
    import(loggingModule)
    import(redirectionModule)
    import(sensorModule)
    import(statusModule)
}