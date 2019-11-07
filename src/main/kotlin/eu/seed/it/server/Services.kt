package eu.seed.it.server

import eu.seed.it.Either
import eu.seed.it.Either.Left
import eu.seed.it.Either.Right
import eu.seed.it.database
import eu.seed.it.database.Seed
import eu.seed.it.database.Sensor
import eu.seed.it.database.SensorSerializable
import eu.seed.it.logger
import eu.seed.it.serialization.UpdateSeedData
import eu.seed.it.server.RequestError.Invalid
import eu.seed.it.server.RequestsSuccess.Created
import eu.seed.it.server.RequestsSuccess.OK
import eu.seed.it.toObject
import spark.Request

val getSeed = object : Get<Seed> {
    override fun invoke(req: Request): Either<RequestError, Seed> {
        val idParam = req.params(":id")
        val id = idParam.toIntOrNull() ?: return Left(Invalid)
        val seed = database.selectSeed(id) ?: return Left(RequestError.NotFound)
        return Right(seed)
    }
}

val getSeeds = object : Get<List<Seed>> {
    override fun invoke(req: Request): Either<RequestError, List<Seed>> {
        val seeds = database.selectSeeds()
        return Right(seeds)
    }
}

val postSeed = object : Post {
    override fun invoke(req: Request): Either<RequestError, RequestsSuccess> {
        val seedEither = req.body().toObject<Seed>()

        if (seedEither is Left) return Left(Invalid)

        seedEither as Right
        val seed = seedEither.value

        val insertion = database.insertSeed(seed)

        return if (insertion) Right(Created)
        else Left(Invalid)
    }
}

val updateSeed = object : Put {
    override fun invoke(req: Request): Either<RequestError, RequestsSuccess> {
        val idParam = req.params(":id")
        val id = idParam.toIntOrNull() ?: return Left(Invalid)


        val objEither = req.body().toObject<UpdateSeedData>()


        if (objEither is Left) return Left(Invalid)
        objEither as Right
        val obj = objEither.value

        val updated = database.updateSeed(id, obj)
        return if (updated) Right(OK)
        else Left(Invalid)
    }

}

val postSensors = object : Post {
    override fun invoke(req: Request): Either<RequestError, RequestsSuccess> {
        val sensorEither = req.body().toObject<SensorSerializable>()

        if (sensorEither is Left) return Left(Invalid)

        sensorEither as Right
        val sensor = sensorEither.value.toSensor()

        if (sensor is Left) return Left(Invalid)

        sensor as Right
        val value: Sensor = sensor.value
        logger.info(value.toString())

        // TODO: insert into db
        val success = true
        return if (success) Right(Created)
        else Left(Invalid)
    }

}