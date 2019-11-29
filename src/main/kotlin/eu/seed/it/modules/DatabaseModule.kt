package eu.seed.it.modules

import eu.seed.it.database.Database
import eu.seed.it.database.DatabaseImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val databaseModule = Kodein.Module(name = "Database") {
    bind<Database>() with singleton { DatabaseImpl() }
}