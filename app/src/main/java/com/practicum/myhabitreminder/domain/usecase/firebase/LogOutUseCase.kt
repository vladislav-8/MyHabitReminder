package com.practicum.myhabitreminder.domain.usecase.firebase

import com.practicum.myhabitreminder.domain.repository.AuthRepository

class LogOutUseCase(private val repository: AuthRepository) {
    operator fun invoke() = repository.logOut()
}