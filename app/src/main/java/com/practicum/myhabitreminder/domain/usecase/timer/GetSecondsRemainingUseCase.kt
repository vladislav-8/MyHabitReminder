package com.practicum.myhabitreminder.domain.usecase.timer

import com.practicum.myhabitreminder.domain.repository.StorageRepository

class GetSecondsRemainingUseCase(private val repository: StorageRepository) {
    fun invoke() = repository.getSecondsRemaining()
}