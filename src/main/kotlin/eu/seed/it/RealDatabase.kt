package eu.seed.it

import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.eq
import me.liuwj.ktorm.entity.findAll
import me.liuwj.ktorm.entity.findOne
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

    override fun seeds() = Seeds.findAll()

    override fun seed(id: Int) = Seeds.findOne { it.id eq id }

    override fun addSeed(seed: Seed): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

