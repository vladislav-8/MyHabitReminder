package com.practicum.myhabitreminder.domain.usecase.timer

import com.practicum.myhabitreminder.domain.repository.StorageRepository
import com.practicum.myhabitreminder.presentation.models.TimerState

class SetTimerStateUseCase(private val repository: StorageRepository) {
    fun invoke(state: TimerState) {
        repository.setTimerState(state)
    }
}