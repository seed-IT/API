package eu.seed.it.server

import eu.seed.it.Either
import spark.Request

interface Service

interface Get<T> : Service {
    operator fun invoke(req: Request): Either<RequestError, T>
}

interface Post : Service {
    operator fun invoke(req: Request): Either<RequestError, RequestsSuccess>
}

interface Put : Service {
    operator fun invoke(req: Request): Either<RequestError, RequestsSuccess>
}

enum class RequestError {
    Invalid,
    NotFound
}

enum class RequestsSuccess {
    OK,
    Created
}