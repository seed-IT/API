package eu.seed.it.server

import eu.seed.it.Either
import eu.seed.it.Either.Left
import eu.seed.it.Either.Right
import eu.seed.it.database
import eu.seed.it.database.Seed
import eu.seed.it.server.RequestError.Invalid
import eu.seed.it.server.RequestsSuccess.Created
import eu.seed.it.toObject
import spark.Request

val getSeed = object : Get<Seed> {
    override fun invoke(req: Request): Either<RequestError, Seed> {
        val idParam = req.params(":id")
        val id = idParam.toIntOrNull() ?: return Left(Invalid)
        val seed = database.seed(id) ?: return Left(RequestError.NotFound)
        return Right(seed)
    }
}

val getSeeds = object : Get<List<Seed>> {
    override fun invoke(req: Request): Either<RequestError, List<Seed>> {
        val seeds = database.seeds()
        return Right(seeds)
    }
}

val postSeed = object : Post {
    override fun invoke(req: Request): Either<RequestError, RequestsSuccess> {
        val seedEither = req.body().toObject<Seed>()

        if (seedEither is Left) return Left(Invalid)

        seedEither as Right
        val seed = seedEither.value

        val insertion = database.addSeed(seed)

        return if (insertion) Right(Created)
        else Left(Invalid)
    }
}