package com.practicum.myhabitreminder.domain.usecase.timer

import com.practicum.myhabitreminder.domain.repository.StorageRepository

class SetPreviousTimerLengthSecondsUseCase(private val repository: StorageRepository) {
    fun invoke(seconds: Long) {
        repository.setPreviousTimerLengthSeconds(seconds)
    }
}