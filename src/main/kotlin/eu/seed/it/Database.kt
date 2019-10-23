package eu.seed.it

interface Database {
    fun connect()

    fun seeds(): List<String>
}