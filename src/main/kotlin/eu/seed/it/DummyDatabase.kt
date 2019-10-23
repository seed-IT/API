package eu.seed.it

class DummyDatabase(private val connection: Connection) : Database {
    override fun connect() {
        println("Attempting connection with $connection")
        println("Not implemented yet")
    }

    override fun seeds() = listOf("Potiron", "Tomates")

}