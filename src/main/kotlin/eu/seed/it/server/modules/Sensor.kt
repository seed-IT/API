package eu.seed.it.server.modules

import eu.seed.it.database.Database
import eu.seed.it.database.Sensor
import eu.seed.it.kodein
import eu.seed.it.server.ServerModule
import eu.seed.it.utils.Either.Left
import eu.seed.it.utils.Either.Right
import eu.seed.it.utils.message
import eu.seed.it.utils.toJson
import eu.seed.it.utils.toObject
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.inSet
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import spark.kotlin.get
import spark.kotlin.post

private class SensorModule : ServerModule {
    private val database: Database by kodein.instance()

    override fun run() {
        get("/sensor") {
            response.header("Access-Control-Allow-Origin", "*")
            database.sensorData().toJson()
        }

        get("/sensor/:id") {
            val id = request.params(":id")
            val idInt = id.toIntOrNull()
            if (idInt == null) {
                response.status(400)
                return@get message("Bad Request")
            }

            response.header("Access-Control-Allow-Origin", "*")
            database.sensorData(idInt).toJson()
        }

        post("/sensor") {
            val sensorEither = request.body().toObject<Sensor>()

            if (sensorEither is Left) {
                response.status(400)
                return@post message("Bad Request")
            }

            sensorEither as Right
            val sensor = sensorEither.value

            database.addSensorData(sensor)

            status(201)
            message("Created")
        }

        post("/sensor/:id") {
            val id = request.params(":id")
            val idInt = id.toIntOrNull()
            if (idInt == null) {
                response.status(400)
                return@post message("Bad Request")
            }

            val sensorEither = request.body().toObject<Sensor>()

            if (sensorEither is Left) {
                response.status(400)
                return@post message("Bad Request")
            }

            sensorEither as Right
            val sensor = sensorEither.value

            database.addSensorData(sensor, idInt)

            status(201)
            message("Created")
        }
    }
}

val sensorModule = Kodein.Module("sensor") {
    bind<ServerModule>().inSet() with singleton { SensorModule() }
}