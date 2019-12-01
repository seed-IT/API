package eu.seed.it.server.modules

import eu.seed.it.Either
import eu.seed.it.Either.Left
import eu.seed.it.Either.Right
import eu.seed.it.database.Database
import eu.seed.it.database.Sensor
import eu.seed.it.kodein
import eu.seed.it.server.ServerModule
import eu.seed.it.server.message
import eu.seed.it.toJson
import eu.seed.it.toObject
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
    }
}

val sensorModule = Kodein.Module("sensor") {
    bind<ServerModule>().inSet() with singleton { SensorModule() }
}