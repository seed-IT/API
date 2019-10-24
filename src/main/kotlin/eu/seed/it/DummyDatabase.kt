package eu.seed.it

import org.slf4j.LoggerFactory

class DummyDatabase(private val connection: Connection) : Database {
    private val logger = LoggerFactory.getLogger("Fake database")
    override fun connect() {
        logger.info("Attempting connection with $connection")
        logger.info("Connection succeeded")
    }

    override fun disconnect() {
        logger.info("Disconnected")
    }

    override fun seeds(): List<Seed> {
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
        return listOf(potiron, tomate)
    }

    override fun seed(id: Int): Seed? {
        return seeds().find { it.id == id }
    }

}