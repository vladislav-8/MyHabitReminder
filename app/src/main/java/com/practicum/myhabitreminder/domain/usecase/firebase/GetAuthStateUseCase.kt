package com.practicum.myhabitreminder.domain.usecase.firebase

import com.practicum.myhabitreminder.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope

class GetAuthStateUseCase(private val authRepository: AuthRepository) {

    operator fun invoke(coroutineScope: CoroutineScope) = authRepository.getAuthState(coroutineScope)

}