package com.mouredev.aristidevslogin.data

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : Result<T>(data)

    //Si hay error no va a haber datos entonces por defecto es null
    class Error<T>(data: T? = null, message: String): Result<T>(data , message)
}