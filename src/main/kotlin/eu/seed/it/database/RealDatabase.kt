package eu.seed.it.database

import eu.seed.it.DatabaseConnection
import eu.seed.it.serialization.SeedProperty.*
import eu.seed.it.serialization.UpdateSeedData
import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.eq
import me.liuwj.ktorm.dsl.insert
import me.liuwj.ktorm.dsl.update
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
        val rowsAffected = Seeds.insert {
            it.name to seed.name
            it.description to seed.description
            it.tips to seed.tips
        }
        return rowsAffected == 1
    }

    override fun updateSeed(id: Int, data: UpdateSeedData): Boolean {
        val rowsAffected = Seeds.update {
            when (data.property) {
                Name -> it.name to data.value
                Description -> it.description to data.value
                Tips -> it.tips to data.value
            }
            where { it.id eq id }
        }
        return rowsAffected == 1
    }

}
