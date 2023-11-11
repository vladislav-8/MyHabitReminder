package com.practicum.myhabitreminder.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.myhabitreminder.domain.usecase.firebase.ResetPasswordUseCase
import com.practicum.myhabitreminder.common.utils.TaskResult
import com.practicum.myhabitreminder.presentation.models.AuthFlowScreenState
import kotlinx.coroutines.launch

class ResetPassViewModel(
    private val resetPassword: ResetPasswordUseCase
) : ViewModel() {

    private val _screenState = MutableLiveData<AuthFlowScreenState>()
    val screenState: MutableLiveData<AuthFlowScreenState> = _screenState

    fun sendNewPassword(email: String) = viewModelScope.launch {
        _screenState.value = AuthFlowScreenState.Loading

        val result = resetPassword(email)
        when (result) {
            is TaskResult.Success -> _screenState.postValue(AuthFlowScreenState.Success)
            is TaskResult.Error -> _screenState.postValue(AuthFlowScreenState.Error(result.errorType))
        }
    }
}
