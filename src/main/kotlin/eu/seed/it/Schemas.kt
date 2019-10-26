package eu.seed.it

import me.liuwj.ktorm.schema.Table
import me.liuwj.ktorm.schema.blob
import me.liuwj.ktorm.schema.int
import me.liuwj.ktorm.schema.varchar

object Seeds : Table<Nothing>("GRAINE") {
    val id by int("ID_GRAINE").primaryKey()
    val name by varchar("NAME_GRAINE")
    val description by varchar("DESCRIPTION")
    val picture by blob("PICTURE")
    val tips by varchar("TIPS")
}