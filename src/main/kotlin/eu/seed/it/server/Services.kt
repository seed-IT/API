package eu.seed.it.server

import eu.seed.it.Either
import eu.seed.it.Either.Left
import eu.seed.it.Either.Right
import eu.seed.it.database.Sensor
import eu.seed.it.server.RequestError.Invalid
import eu.seed.it.server.RequestsSuccess.Created
import eu.seed.it.toObject
import spark.Request
import java.util.concurrent.CopyOnWriteArrayList


val sensorData = CopyOnWriteArrayList<Sensor>()

val postSensors = object : Post {
    override fun invoke(req: Request): Either<RequestError, RequestsSuccess> {
        val sensorEither = req.body().toObject<Sensor>()

        if (sensorEither is Left) return Left(Invalid)
        sensorEither as Right
        val sensor = sensorEither.value

        sensorData.add(sensor)
        return Right(Created)
    }

}