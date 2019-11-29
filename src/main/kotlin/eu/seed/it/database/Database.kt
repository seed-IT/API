package eu.seed.it.database


interface Database {
    fun connect()

    fun disconnect()

    fun sensorData() : List<Sensor>

    fun addSensorData(sensor: Sensor)
}