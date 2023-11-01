package com.practicum.myhabitreminder.domain.usecase

import com.practicum.myhabitreminder.domain.repository.AuthRepository

class SignUpUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String) = repository.signUp(email, password)
}