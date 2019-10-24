package eu.seed.it

import eu.seed.it.mappings.Seed
import java.util.*

interface Database {
    fun connect()

    fun disconnect()

    fun seeds(): List<Seed>

    fun seed(id: Int): Seed?
}