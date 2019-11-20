package eu.seed.it.database

import me.liuwj.ktorm.dsl.QueryRowSet
import me.liuwj.ktorm.schema.BaseTable
import me.liuwj.ktorm.schema.float
import java.time.LocalDateTime
import me.liuwj.ktorm.schema.datetime as ktormDatetime


data class Sensor(
        val datetime: LocalDateTime,
        val temperature: Float,
        val humidity: Float,
        val pressure: Float
)

object Sensors : BaseTable<Sensor>("SENSOR") {
    val datetime by ktormDatetime("DATETIME")
    val temperature by float("TEMPERATURE")
    val humidity by float("HUMIDITY")
    val pressure by float("PRESSURE")

    override fun doCreateEntity(row: QueryRowSet, withReferences: Boolean): Sensor {
        return Sensor(
                datetime = row[datetime]!!,
                temperature = row[temperature]!!,
                humidity = row[humidity]!!,
                pressure = row[pressure]!!
        )
    }
}
