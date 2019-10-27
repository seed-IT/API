package eu.seed.it.server

import eu.seed.it.Either
import eu.seed.it.database.Database
import spark.Request

interface Service

interface Get<T> : Service {
    operator fun invoke(database: Database, req: Request): Either<RequestError, T>
}

interface Post : Service {
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