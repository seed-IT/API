package eu.seed.it.database.schemas

import me.liuwj.ktorm.schema.Table
import me.liuwj.ktorm.schema.long
import me.liuwj.ktorm.schema.varchar

object UsersTable : Table<Nothing>("users") {
    val id by long("id").primaryKey()
    val email by varchar("email")
}