package com.practicum.myhabitreminder.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.practicum.myhabitreminder.domain.repository.StorageRepository
import com.practicum.myhabitreminder.presentation.models.TimerState

class TimerExpiredReceiver(private val storageRepository: StorageRepository) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        storageRepository.setTimerState(TimerState.STOPPED)
        storageRepository.setAlarmSetTime(0)
    }
}