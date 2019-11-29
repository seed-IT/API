package eu.seed.it.database

import eu.seed.it.configuration.Configuration
import eu.seed.it.kodein
import org.kodein.di.generic.instance
import me.liuwj.ktorm.database.Database as KtormDatabase


class DatabaseImpl : Database {
    private val configuration: Configuration by kodein.instance()
    private val connection = configuration.databaseConnection()

    override fun connect() {
        val url = "jdbc:mariadb://${connection.address}:${connection.port}/${connection.name}"

        KtormDatabase.connect(
                url = url,
                driver = "org.mariadb.jdbc.Driver",
                user = connection.user,
                password = connection.password
        )


    }

    override fun disconnect() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
