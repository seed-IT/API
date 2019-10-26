package eu.seed.it

import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.select
import eu.seed.it.Database as Database1


class RealDatabase(private val connection: Connection) : Database1 {
    override fun connect() {
        val url = "jdbc:mariadb://${connection.address}:${connection.port}/seeds"

        Database.connect(
                url = url,
                driver = "org.mariadb.jdbc.Driver",
                user = "seeds",
                password = "tortue"
        )

        Seeds.select()
                .map { row ->
                    val name = row[Seeds.name]
                    val description = row[Seeds.description]

                    "name=$name description=$description"
                }.forEach { logger.info(it) }

    }

    override fun disconnect() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun seeds(): List<Seed> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun seed(id: Int): Seed? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addSeed(seed: Seed): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

