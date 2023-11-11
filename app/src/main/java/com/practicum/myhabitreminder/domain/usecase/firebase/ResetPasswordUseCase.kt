package com.practicum.myhabitreminder.domain.usecase.firebase

import com.practicum.myhabitreminder.domain.repository.AuthRepository

class ResetPasswordUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(email: String) = repository.sendNewPassword(email)
}