package com.practicum.myhabitreminder.presentation.models

import com.practicum.myhabitreminder.common.utils.ErrorType

sealed class AuthFlowScreenState  {
    object Loading : AuthFlowScreenState()
    object Success : AuthFlowScreenState()
    data class Error(val errorType: ErrorType) : AuthFlowScreenState()
}
