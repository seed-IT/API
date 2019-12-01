package eu.seed.it.database


interface Database {
    fun connect()

    fun disconnect()

    fun sensorData(id: Int = 0): List<Sensor>

    fun addSensorData(sensor: Sensor, id: Int = 0)
}