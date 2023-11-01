package com.practicum.myhabitreminder.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.myhabitreminder.common.utils.TaskResult
import com.practicum.myhabitreminder.domain.usecase.SignInUseCase
import com.practicum.myhabitreminder.presentation.models.AuthFlowScreenState
import kotlinx.coroutines.launch

class AuthorizationViewModel(
    private val signIn: SignInUseCase
) : ViewModel() {

    private val _screenState = MutableLiveData<AuthFlowScreenState>()
    val screenState: LiveData<AuthFlowScreenState> = _screenState

    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        _screenState.value = AuthFlowScreenState.Loading

        val authResult = signIn(email, password)
        when (authResult) {
            is TaskResult.Success -> _screenState.postValue(AuthFlowScreenState.Success)
            is TaskResult.Error -> _screenState.postValue(AuthFlowScreenState.Error(authResult.errorType))
        }
    }
}