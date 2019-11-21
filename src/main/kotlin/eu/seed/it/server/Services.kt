package eu.seed.it.server

import eu.seed.it.Either
import eu.seed.it.Either.Left
import eu.seed.it.Either.Right
import eu.seed.it.database.Sensor
import eu.seed.it.server.RequestError.Invalid
import eu.seed.it.server.RequestsSuccess.Created
import eu.seed.it.toObject
import spark.Request
import java.util.concurrent.ConcurrentLinkedDeque


val capacity = 20
val sensorData = ConcurrentLinkedDeque<Sensor>()

val postSensor = object : Post {
    override fun invoke(req: Request): Either<RequestError, RequestsSuccess> {
        val sensorEither = req.body().toObject<Sensor>()

        if (sensorEither is Left) return Left(Invalid)
        sensorEither as Right
        val sensor = sensorEither.value

        if (sensorData.size == capacity) {
            sensorData.removeFirst()
        }
        sensorData.add(sensor)
        return Right(Created)
    }
}

val getSensor = object : Get<List<Sensor>> {
    override fun invoke(req: Request): Either<RequestError, List<Sensor>> {
        return Right(sensorData.toList())
    }

}