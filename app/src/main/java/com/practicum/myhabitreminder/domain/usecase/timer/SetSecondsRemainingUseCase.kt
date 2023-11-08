package com.practicum.myhabitreminder.domain.usecase.timer

import com.practicum.myhabitreminder.domain.repository.StorageRepository

class SetSecondsRemainingUseCase(private val repository: StorageRepository) {
    fun invoke(seconds: Long) {
        repository.setSecondsRemaining(seconds)
    }
}