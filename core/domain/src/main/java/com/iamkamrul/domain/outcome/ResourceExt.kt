package com.iamkamrul.domain.outcome

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

fun <T, R> Flow<Resource<T>>.mapResult(
    transform: suspend (T) -> R
): Flow<Resource<R>> = map { resource ->
    when (resource) {
        is Resource.Success -> Resource.Success(transform(resource.data))
        is Resource.Error -> resource
        is Resource.Loading -> Resource.Loading
    }
}

/// Maps the success value to another Result — useful for chaining operations that can fail.
fun <T, R> Flow<Resource<T>>.flatMapResult(
    transform: suspend (T) -> Resource<R>
): Flow<Resource<R>> = map { resource ->
    when (resource) {
        is Resource.Success -> transform(resource.data)
        is Resource.Error -> resource
        is Resource.Loading -> Resource.Loading
    }
}

/// Side effect on success, doesn't change the result. Useful for caching, logging.
fun <T> Flow<Resource<T>>.onResultSuccess(
    action: suspend (T) -> Unit
): Flow<Resource<T>> = onEach { resource ->
    if (resource is Resource.Success) action(resource.data)
}

/// Side effect on error.
fun <T> Flow<Resource<T>>.onResultError(
    action: suspend (DataError) -> Unit
): Flow<Resource<T>> = onEach { resource ->
    if (resource is Resource.Error) action(resource.error as DataError)
}