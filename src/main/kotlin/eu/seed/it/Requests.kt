package eu.seed.it

import com.fasterxml.jackson.module.kotlin.readValue
import eu.seed.it.Either.Left
import eu.seed.it.Either.Right
import eu.seed.it.RequestError.Invalid
import eu.seed.it.RequestsSuccess.Created
import spark.Request

interface Get<T> {
    operator fun invoke(database: Database, req: Request): Either<RequestError, T>
}

interface Post {
    operator fun invoke(database: Database, req: Request): Either<RequestError, RequestsSuccess>
}


enum class RequestError {
    Invalid,
    NotFound
}

enum class RequestsSuccess {
    OK,
    Created
}

val getSeed = object : Get<Seed> {
    override fun invoke(database: Database, req: Request): Either<RequestError, Seed> {
        val idParam = req.params(":id")
        val id = idParam.toIntOrNull() ?: return Left(Invalid)
        val seed = database.seed(id) ?: return Left(RequestError.NotFound)
        return Right(seed)
    }
}

val getSeeds = object : Get<List<Seed>> {
    override fun invoke(database: Database, req: Request): Either<RequestError, List<Seed>> {
        val seeds = database.seeds()
        return Right(seeds)
    }
}

val postSeed = object : Post {
    override fun invoke(database: Database, req: Request): Either<RequestError, RequestsSuccess> {
        val json = req.body()
        lateinit var seed: Seed
        try {
            seed = mapper.readValue(json)
        } catch (e: java.lang.Exception) {
            return Left(Invalid)
        }

        val insertion = database.addSeed(seed)
        return if (insertion) Right(Created)
        else Left(Invalid)
    }
}