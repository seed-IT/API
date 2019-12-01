package eu.seed.it.database.schemas

import me.liuwj.ktorm.schema.Table
import me.liuwj.ktorm.schema.float
import me.liuwj.ktorm.schema.long
import java.time.LocalDateTime
import me.liuwj.ktorm.schema.datetime as ktormDatetime


data class Sensor(
        val datetime: LocalDateTime,
        val temperature: Float,
        val humidity: Float,
        val pressure: Float
)

object SensorTable : Table<Nothing>("sensor") {
    val id by long("id")
    val datetime by ktormDatetime("datetime")
    val temperature by float("temperature")
    val humidity by float("humidity")
    val pressure by float("pressure")
}
