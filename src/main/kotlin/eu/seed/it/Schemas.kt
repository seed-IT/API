package eu.seed.it

import me.liuwj.ktorm.dsl.QueryRowSet
import me.liuwj.ktorm.schema.BaseTable
import me.liuwj.ktorm.schema.int
import me.liuwj.ktorm.schema.varchar
import java.time.LocalDate

object Seeds : BaseTable<Seed>("GRAINE") {
    val id by int("ID_GRAINE").primaryKey()
    val name by varchar("NAME_GRAINE")
    val description by varchar("DESCRIPTION")
    val tips by varchar("TIPS")

    override fun doCreateEntity(row: QueryRowSet, withReferences: Boolean): Seed {
        return Seed(
                id = row[id],
                name = row[name]!!,
                description = row[description],
                tips = row[tips]
        )
    }
}

// TODO: pictures
data class Seed(
        val id: Int? = null,
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
        return id ?: 0
    }
}

data class User(
        val id: Int,
        val name: String,
        val lastName: String,
        val birthDate: LocalDate,
        val email: String,
        val password: String,
        val subscription: String,
        val location: String
)