package eu.seed.it.database

import eu.seed.it.configuration.Configuration
import eu.seed.it.database.schemas.Sensor
import eu.seed.it.database.schemas.SensorTable
import eu.seed.it.database.schemas.UsersTable
import eu.seed.it.kodein
import me.liuwj.ktorm.dsl.eq
import me.liuwj.ktorm.dsl.insert
import me.liuwj.ktorm.dsl.select
import me.liuwj.ktorm.dsl.where
import me.liuwj.ktorm.logging.Slf4jLoggerAdapter
import org.kodein.di.generic.instance
import org.slf4j.Logger
import kotlin.math.min
import me.liuwj.ktorm.database.Database as KtormDatabase


class MariaDB : Database {
    private val configuration: Configuration by kodein.instance()
    private val connection = configuration.databaseConnection()
    private val logger: Logger by kodein.instance()

    override fun connect() {
        val url = "jdbc:mariadb://${connection.address}:${connection.port}/${connection.name}"

        KtormDatabase.connect(
                url = url,
                driver = "org.mariadb.jdbc.Driver",
                user = connection.user,
                password = connection.password,
                logger = Slf4jLoggerAdapter(logger)
        )

    }


    override fun sensorData(id: Long): List<Sensor> {
        val results = SensorTable.select()
                .where { SensorTable.id eq id }
                .map {
                    Sensor(
                            it[SensorTable.datetime]!!,
                            it[SensorTable.temperature]!!,
                            it[SensorTable.humidity]!!,
                            it[SensorTable.pressure]!!
                    )
                }

        val max = min(results.size, 20)
        return results.subList(0, max)
    }

    override fun addSensorData(sensor: Sensor, id: Long) {
        SensorTable.insert {
            it.id to id
            it.datetime to sensor.datetime
            it.temperature to sensor.temperature
            it.humidity to sensor.humidity
            it.pressure to sensor.pressure
        }
    }

    override fun userId(email: String): Long? {
        val matches = UsersTable.select(UsersTable.id, UsersTable.email)
                .map { it[UsersTable.id] to it[UsersTable.email] }
                .filter { it.second == email }

        logger.debug(matches.toString())

        return when (matches.size) {
            1 -> matches[0].first
            else -> null
        }
    }

}
