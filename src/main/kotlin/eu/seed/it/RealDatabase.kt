package eu.seed.it

import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.select
import eu.seed.it.Database as Database1


class RealDatabase(private val connection: DatabaseConnection) : Database1 {
    override fun connect() {
        val url = "jdbc:mariadb://${connection.address}:${connection.port.value}/${connection.name}"

        Database.connect(
                url = url,
                driver = "org.mariadb.jdbc.Driver",
                user = connection.user,
                password = connection.password
        )

    }

    override fun disconnect() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun seeds() = Seeds.select()
            .map { row ->
                Seed(
                        id = row[Seeds.id],
                        name = row[Seeds.name]!!,
                        description = row[Seeds.description]!!,
                        tips = row[Seeds.tips]
                )
            }

    override fun seed(id: Int): Seed? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addSeed(seed: Seed): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

