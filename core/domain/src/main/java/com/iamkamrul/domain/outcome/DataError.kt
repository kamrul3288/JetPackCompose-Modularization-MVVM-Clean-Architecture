package com.iamkamrul.domain.outcome

sealed interface DataError {

    sealed interface Network : DataError {
        data object NoInternet : Network
        data object Timeout : Network
        data object UnknownHost : Network
        data object ConnectionShutdown : Network
        data object SslHandshake : Network
    }

    sealed interface Http : DataError {
        val code: Int

        data class BadRequest(override val code: Int) : Http
        data class Unauthorized(override val code: Int) : Http
        data class Forbidden(override val code: Int) : Http
        data class NotFound(override val code: Int) : Http
        data class InternalServerError(override val code: Int) : Http
        data class Conflict(override val code: Int) : Http
        data class TooManyRequests(override val code: Int) : Http
        data class Unknown(override val code: Int, val serverMessage: String? = null) : Http
    }


    sealed interface Local : DataError {
        data object EmptyResponse : Local
        data object ParseError : Local
        data object DiskFull : Local
        data object Unknown : Local
    }

    data class ServerMessage(val message: String, val code: Int) : DataError

}