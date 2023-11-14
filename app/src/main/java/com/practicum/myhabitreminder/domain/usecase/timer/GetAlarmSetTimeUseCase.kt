package com.practicum.myhabitreminder.domain.usecase.timer

import com.practicum.myhabitreminder.domain.repository.StorageRepository

class GetAlarmSetTimeUseCase(private val repository: StorageRepository) {
    fun invoke() = repository.getAlarmSetTime()
}