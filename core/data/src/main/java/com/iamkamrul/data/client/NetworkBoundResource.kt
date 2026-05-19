package com.iamkamrul.data.client

import com.iamkamrul.di.qualifier.IoDispatcher
import com.iamkamrul.domain.outcome.DataError
import com.iamkamrul.domain.outcome.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class NetworkBoundResource @Inject constructor(
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val errorParser: NetworkErrorParser,
) {
    fun <T> downloadData(api: suspend () -> Response<T>): Flow<Resource<T>> = flow {
        emit(Resource.Loading)
        val response = api()
        emit(handleResponse(response))
    }
        .catch { throwable ->
            Timber.e(throwable, "Network call failed")
            emit(Resource.Error(errorParser.mapThrowable(throwable)))
        }
        .flowOn(ioDispatcher)

    private fun <T> handleResponse(response: Response<T>): Resource<T> {
        if (response.isSuccessful) {
            val body = response.body()
            return if (body != null) {
                Resource.Success(body)
            } else {
                Resource.Error(DataError.Local.EmptyResponse)
            }
        }
        return Resource.Error(errorParser.mapHttpError(response.code(), response.errorBody()))
    }
}