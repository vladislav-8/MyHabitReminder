package com.practicum.myhabitreminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.practicum.myhabitreminder.domain.usecase.firebase.GetAuthStateUseCase

class MainActivityViewModel(private val getAuthStateUseCase: GetAuthStateUseCase) : ViewModel() {

    private var viewModelIsReady = false
    private var firstEmit = true

    fun getAuthState() = liveData<Boolean> {
        getAuthStateUseCase(viewModelScope).collect { state ->
            emit(state)

            if (!firstEmit) {
                viewModelIsReady = true
            }

            firstEmit = false
        }
    }
}