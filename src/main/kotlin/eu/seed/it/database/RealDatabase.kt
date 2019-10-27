package eu.seed.it.database

import eu.seed.it.DatabaseConnection
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.eq
import me.liuwj.ktorm.entity.findAll
import me.liuwj.ktorm.entity.findOne
import eu.seed.it.database.Database as Database1


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

    override fun selectSeeds() = Seeds.findAll()

    override fun selectSeed(id: Int) = Seeds.findOne { Seeds.id eq id }

    override fun insertSeed(seed: Seed): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

