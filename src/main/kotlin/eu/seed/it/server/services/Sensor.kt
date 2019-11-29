package eu.seed.it.server.services

import eu.seed.it.Either
import eu.seed.it.Either.Left
import eu.seed.it.Either.Right
import eu.seed.it.database.Database
import eu.seed.it.database.Sensor
import eu.seed.it.kodein
import eu.seed.it.server.Get
import eu.seed.it.server.Post
import eu.seed.it.server.RequestError
import eu.seed.it.server.RequestError.Invalid
import eu.seed.it.server.RequestsSuccess
import eu.seed.it.server.RequestsSuccess.Created
import eu.seed.it.toObject
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.inSet
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import spark.Request

val sensorModule = Kodein.Module(name = "Sensor") {
    bind<Get<out Any>>().inSet() with singleton { GetSensor() }
    bind<Post>().inSet() with singleton { PostSensor() }
}

private val database: Database by kodein.instance()

class PostSensor : Post {
    override val path: String = "/sensor"

    override fun invoke(req: Request): Either<RequestError, RequestsSuccess> {
        val sensorEither = req.body().toObject<Sensor>()

        if (sensorEither is Left) return Left(Invalid)
        sensorEither as Right
        val sensor = sensorEither.value

        database.addSensorData(sensor)
        return Right(Created)
    }
}

class GetSensor : Get<List<Sensor>> {
    override val path: String = "/sensor"

    override fun invoke(req: Request): Either<RequestError, List<Sensor>> {
        return Right(database.sensorData())
    }

}