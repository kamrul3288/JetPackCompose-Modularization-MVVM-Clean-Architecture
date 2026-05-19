package com.iamkamrul.data.client

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.SerializedName
import com.iamkamrul.domain.connectivity.ConnectivityObserver
import com.iamkamrul.domain.outcome.DataError
import okhttp3.ResponseBody
import okhttp3.internal.http2.ConnectionShutdownException
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

class NetworkErrorParser @Inject constructor(
    private val gson: Gson,
    private val connectivityObserver: ConnectivityObserver
) {
    fun mapThrowable(throwable: Throwable): DataError = when (throwable) {
        is UnknownHostException -> {
            if (connectivityObserver.isConnected()) {
                DataError.Network.UnknownHost // truly bad host / DNS issue
            } else {
                DataError.Network.NoInternet // no connection
            }
        }

        is SocketTimeoutException -> {
            if (connectivityObserver.isConnected()) {
                DataError.Network.Timeout       // server is slow
            } else {
                DataError.Network.NoInternet    // lost connection mid-request
            }
        }

        is SSLHandshakeException -> DataError.Network.SslHandshake
        is ConnectionShutdownException -> {
            if (connectivityObserver.isConnected()) {
                DataError.Network.ConnectionShutdown
            } else {
                DataError.Network.NoInternet
            }
        }

        is IOException -> {
            if (connectivityObserver.isConnected()) {
                DataError.Local.Unknown
            } else {
                DataError.Network.NoInternet
            }
        }

        is HttpException -> mapHttpError(throwable.code(), throwable.response()?.errorBody())
        is JsonSyntaxException, is JsonParseException -> DataError.Local.ParseError
        else -> DataError.Local.Unknown
    }

    fun mapHttpError(code: Int, errorBody: ResponseBody?): DataError {
        val serverMessage = parseServerMessage(errorBody)

        if (!serverMessage.isNullOrBlank()) {
            return DataError.ServerMessage(serverMessage, code)
        }

        return when (code) {
            400 -> DataError.Http.BadRequest(code)
            401 -> DataError.Http.Unauthorized(code)
            403 -> DataError.Http.Forbidden(code)
            404 -> DataError.Http.NotFound(code)
            409 -> DataError.Http.Conflict(code)
            429 -> DataError.Http.TooManyRequests(code)
            in 500..599 -> DataError.Http.InternalServerError(code)
            else -> DataError.Http.Unknown(code, serverMessage)
        }
    }

    private fun parseServerMessage(errorBody: ResponseBody?): String? {
        if (errorBody == null) return null
        return try {
            // errorBody.string() can only be called ONCE — read into a local
            val raw = errorBody.string()
            if (raw.isBlank()) return null

            val errorResponse = gson.fromJson(raw, ApiErrorResponse::class.java)
            errorResponse?.message?.takeIf { it.isNotBlank() }
                ?: errorResponse?.error?.takeIf { it.isNotBlank() }
        } catch (e: JsonSyntaxException) {
            Timber.w(e, "Failed to parse error body as JSON")
            null
        } catch (e: Exception) {
            Timber.w(e, "Unexpected error parsing error body")
            null
        }
    }
}

private data class ApiErrorResponse(
    @SerializedName("message") val message: String? = null,
    @SerializedName("error") val error: String? = null,
    @SerializedName("code") val code: Int? = null,
)

//@HiltViewModel
//class ConnectivityViewModel @Inject constructor(
//    observer: ConnectivityObserver,
//) : ViewModel() {
//    val isConnected: StateFlow<Boolean> = observer.observe()
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), true)
//}
//
//// In your root Composable
//val isConnected by connectivityViewModel.isConnected.collectAsStateWithLifecycle()
//AnimatedVisibility(visible = !isConnected) {
//    NoInternetBanner()
//}