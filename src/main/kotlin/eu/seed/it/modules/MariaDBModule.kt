package eu.seed.it.modules

import eu.seed.it.database.Database
import eu.seed.it.database.MariaDB
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val mariaDBModule = Kodein.Module(name = "MariaDB") {
    bind<Database>() with singleton { MariaDB() }
}