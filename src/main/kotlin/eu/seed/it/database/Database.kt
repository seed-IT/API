package eu.seed.it.database

interface Database {
    fun connect()

    fun disconnect()

    fun seeds(): List<Seed>

    fun seed(id: Int): Seed?

    fun addSeed(seed: Seed): Boolean
}