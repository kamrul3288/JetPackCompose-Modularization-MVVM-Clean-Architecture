package com.iamkamrul.domain.utils


sealed interface Result<out R> {
    data object Loading:Result<Nothing>
    data class Success<out T>(val data: T) : Result<T>
    data class Error<out T>(val message: String,val code:Int) : Result<T>
}
