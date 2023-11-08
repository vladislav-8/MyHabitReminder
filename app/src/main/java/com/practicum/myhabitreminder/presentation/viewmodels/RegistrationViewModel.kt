package com.practicum.myhabitreminder.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.myhabitreminder.common.utils.TaskResult
import com.practicum.myhabitreminder.domain.usecase.firebase.SignUpUseCase
import com.practicum.myhabitreminder.presentation.models.AuthFlowScreenState
import kotlinx.coroutines.launch

class RegistrationViewModel(private val signUp: SignUpUseCase): ViewModel() {

    private val _screenState = MutableLiveData<AuthFlowScreenState>()
    val screenState: MutableLiveData<AuthFlowScreenState> = _screenState

    fun signUpWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        _screenState.value = AuthFlowScreenState.Loading

        val authResult = signUp(email, password)
        when (authResult) {
            is TaskResult.Success -> _screenState.postValue(AuthFlowScreenState.Success)
            is TaskResult.Error -> _screenState.postValue(AuthFlowScreenState.Error(authResult.errorType))
        }
    }
}