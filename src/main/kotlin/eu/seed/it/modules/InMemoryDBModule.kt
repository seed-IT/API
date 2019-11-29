package eu.seed.it.modules

import eu.seed.it.database.Database
import eu.seed.it.database.InMemoryDB
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val inMemoryDBModule = Kodein.Module(name = "InMemoryDB") {
    bind<Database>() with singleton { InMemoryDB() }
}