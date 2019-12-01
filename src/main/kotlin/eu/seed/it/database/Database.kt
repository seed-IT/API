package eu.seed.it.database

import eu.seed.it.database.schemas.Sensor


interface Database {
    fun connect()

    fun sensorData(id: Long = 0): List<Sensor>

    fun addSensorData(sensor: Sensor, id: Long = 0)

    fun userId(email: String): Long?
}