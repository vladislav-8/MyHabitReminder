package com.practicum.myhabitreminder.domain.usecase.timer

import com.practicum.myhabitreminder.domain.repository.StorageRepository

class GetTimerStateUseCase(private val repository: StorageRepository) {

    fun invoke() = repository.getTimerState()
}