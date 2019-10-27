package eu.seed.it.database

import eu.seed.it.serialization.UpdateSeedData

interface Database {
    fun connect()

    fun disconnect()

    fun selectSeeds(): List<Seed>

    fun selectSeed(id: Int): Seed?

    fun insertSeed(seed: Seed): Boolean

    fun updateSeed(id: Int, data: UpdateSeedData): Boolean
}