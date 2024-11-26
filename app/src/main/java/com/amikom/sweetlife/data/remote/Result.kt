package com.amikom.sweetlife.data.remote

sealed class Result<out R> private constructor() {
    data object Empty: Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: String) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}
