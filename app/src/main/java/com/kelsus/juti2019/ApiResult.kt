package com.kelsus.juti2019

sealed class ApiResult<out T : Any, out P : Any> {
    data class Success<out T : Any>(val data: T) : ApiResult<T, Nothing>()
    data class NonSuccess<P : Any>(val body: P, val code: Int) : ApiResult<Nothing, P>()
    data class Failure(val exception: Exception) : ApiResult<Nothing, Nothing>()
}