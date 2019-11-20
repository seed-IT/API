package eu.seed.it.database

import me.liuwj.ktorm.dsl.QueryRowSet
import me.liuwj.ktorm.schema.BaseTable
import me.liuwj.ktorm.schema.float
import me.liuwj.ktorm.schema.int
import me.liuwj.ktorm.schema.varchar
import java.time.LocalDateTime
import me.liuwj.ktorm.schema.datetime as ktormDatetime

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
