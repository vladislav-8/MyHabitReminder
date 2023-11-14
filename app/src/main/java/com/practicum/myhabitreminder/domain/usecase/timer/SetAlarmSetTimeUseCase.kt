package com.practicum.myhabitreminder.domain.usecase.timer

import com.practicum.myhabitreminder.domain.repository.StorageRepository

class SetAlarmSetTimeUseCase(private val repository: StorageRepository) {
    fun invoke(time: Long) = repository.setAlarmSetTime(time)
}
