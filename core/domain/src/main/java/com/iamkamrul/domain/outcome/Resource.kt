package com.iamkamrul.domain.outcome

sealed class Resource<out T> {
    data object Loading : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val error: DataError) : Resource<Nothing>()
}