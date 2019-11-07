package eu.seed.it.database

import com.fasterxml.jackson.annotation.JsonProperty
import eu.seed.it.Either
import me.liuwj.ktorm.dsl.QueryRowSet
import me.liuwj.ktorm.schema.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object Seeds : BaseTable<Seed>("GRAINE") {
    val id by int("ID_GRAINE").primaryKey()
    val name by varchar("NAME_GRAINE")
    val description by varchar("DESCRIPTION")
    val tips by varchar("TIPS")

    override fun doCreateEntity(row: QueryRowSet, withReferences: Boolean): Seed {
        return Seed(
                id = row[id]!!,
                name = row[name]!!,
                description = row[description],
                tips = row[tips]
        )
    }
}


// TODO: pictures
data class Seed(
        val id: Int,
        val name: String,
        val description: String?,
        val tips: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Seed

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}

data class Sensor(
        @JsonProperty("datetime")
        val dateTime: LocalDateTime,
        val temperature: Float,
        val humidity: Float,
        val pressure: Float
)

data class SensorSerializable(
        @JsonProperty("datetime")
        val dateTime: String,
        val temperature: Float,
        val humidity: Float,
        val pressure: Float
) {
    fun toSensor(): Either<DateTimeParseException, Sensor> {
        return try {
            val dateTime1 = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME)
            val sensor = Sensor(dateTime1, temperature, humidity, pressure)
            Either.Right(sensor)
        } catch (e: DateTimeParseException) {
            Either.Left(e)
        }
    }
}

object Sensors : BaseTable<Sensor>("SENSOR") {
    val dateTime by datetime("DATETIME")
    val temperature by float("TEMPERATURE")
    val humidity by float("HUMIDITY")
    val pressure by float("PRESSURE")

    override fun doCreateEntity(row: QueryRowSet, withReferences: Boolean): Sensor {
        return Sensor(
                dateTime = row[dateTime]!!,
                temperature = row[temperature]!!,
                humidity = row[humidity]!!,
                pressure = row[pressure]!!
        )
    }
}
