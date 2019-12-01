package eu.seed.it.database

import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedDeque

class InMemoryDB : Database {
    override fun connect() {
        // Nothing to do
    }

    override fun disconnect() {
        // Nothing to do
    }

    private val multimap: MutableMap<Int, Deque<Sensor>> = ConcurrentHashMap()
    private val capacity = 20

    override fun sensorData(id: Int): List<Sensor> {
        return multimap[id]?.toList() ?: emptyList()
    }

    override fun addSensorData(sensor: Sensor, id: Int) {
        var current = multimap[id]
        if (current == null) {
            current = ConcurrentLinkedDeque()
            multimap[id] = current
        }

        if (current.size == capacity) {
            current.removeFirst()
        }
        current.add(sensor)
    }
}