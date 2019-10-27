package eu.seed.it.database

import eu.seed.it.Connection
import org.slf4j.LoggerFactory

class DummyDatabase(private val connection: Connection) : Database {
    private val logger = LoggerFactory.getLogger("Fake database")
    private val seedList: MutableList<Seed>

    init {
        val potiron = Seed(
                0,
                "Potiron",
                "Une des cinq espèces de courges les plus couramment cultivées"
        )
        val tomate = Seed(
                1,
                "Tomate",
                "Une espèce de plantes herbacées"
        )
        seedList = mutableListOf(potiron, tomate)
    }

    override fun connect() {
        logger.info("Attempting connection with $connection")
        logger.info("Connection succeeded")
    }

    override fun disconnect() {
        logger.info("Disconnected")
    }

    override fun seeds() = seedList

    override fun seed(id: Int): Seed? {
        return seeds().find { it.id == id }
    }

    override fun addSeed(seed: Seed): Boolean {
        // In a real database, the id will be set by inserting the seed
        fun newID(): Int = seeds().map { it.id!! }.max() ?: 0

        val newSeed = Seed(
                id = newID(),
                name = seed.name,
                description = seed.description,
                tips = seed.tips
        )

        seedList.add(seed)
        // always succeed
        return true
    }


}