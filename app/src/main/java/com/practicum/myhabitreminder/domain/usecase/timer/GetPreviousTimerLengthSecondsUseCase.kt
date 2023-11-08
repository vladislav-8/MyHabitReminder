package com.practicum.myhabitreminder.domain.usecase.timer

import com.practicum.myhabitreminder.domain.repository.StorageRepository

class GetPreviousTimerLengthSecondsUseCase(private val repository: StorageRepository) {

    fun invoke() = repository.getPreviousTimerLengthSeconds()
}