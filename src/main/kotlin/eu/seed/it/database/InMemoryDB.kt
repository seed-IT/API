package eu.seed.it.database

import java.util.concurrent.ConcurrentLinkedDeque

class InMemoryDB : Database {
    override fun connect() {
        // Nothing to do
    }

    override fun disconnect() {
        // Nothing to do
    }

    private val sensorDataDeque = ConcurrentLinkedDeque<Sensor>()
    private val capacity = 20

    override fun sensorData(): List<Sensor> {
        return sensorDataDeque.toList()
    }

    override fun addSensorData(sensor: Sensor) {
        if (sensorDataDeque.size == capacity) {
            sensorDataDeque.removeFirst()
        }
        sensorDataDeque.add(sensor)
    }
}