package eu.seed.it.configuration

import eu.seed.it.Connection
import eu.seed.it.DatabaseConnection

interface Configuration {
    fun load()

    fun serverConnection(): Connection

    fun databaseConnection(): DatabaseConnection
}