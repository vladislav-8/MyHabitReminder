package com.practicum.myhabitreminder.common.utils

sealed class TaskResult<out T> {
    data class Success<out T>(val data: T?) : TaskResult<T>()
    data class Error(val errorType: ErrorType) : TaskResult<Nothing>()
}
