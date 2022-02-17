package com.zialab.domain.common

import com.zialab.domain.entities.ResultSearchUI

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Loading<T>(val data: T?) : Result<T>()
    data class Failure<T>(val error: ResultSearchUI?, val httpCode: Int) : Result<T>()
}