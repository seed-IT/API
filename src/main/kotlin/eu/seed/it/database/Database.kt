package eu.seed.it.database

interface Database {
    fun connect()

    fun disconnect()

    fun selectSeeds(): List<Seed>

    fun selectSeed(id: Int): Seed?

    fun insertSeed(seed: Seed): Boolean
}