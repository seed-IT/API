package eu.seed.it.modules

import eu.seed.it.configuration.Configuration
import eu.seed.it.configuration.ConfigurationImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import java.io.File

val configurationModule = Kodein.Module(name = "Configuration") {
    bind<File>(tag = "configFile") with singleton {
        val cwd = System.getProperty("user.dir")
        File(cwd, "config.toml")
    }

    bind<Configuration>() with singleton { ConfigurationImpl() }
}