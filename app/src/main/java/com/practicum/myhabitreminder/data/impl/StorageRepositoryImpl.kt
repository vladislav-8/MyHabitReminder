package com.practicum.myhabitreminder.data.impl

import android.content.SharedPreferences
import com.practicum.myhabitreminder.common.utils.PREVIOUS_TIMER_LENGTH_SECONDS_ID
import com.practicum.myhabitreminder.common.utils.SECONDS_REMAINING_ID
import com.practicum.myhabitreminder.common.utils.TIMER_STATE_ID
import com.practicum.myhabitreminder.domain.repository.StorageRepository
import com.practicum.myhabitreminder.presentation.models.TimerState

class StorageRepositoryImpl(private val sharedPreferences: SharedPreferences) : StorageRepository {

    override fun getTimerLength(): Int {
        return 1
    }

    override fun getPreviousTimerLengthSeconds(): Long {
        return sharedPreferences.getLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, 0) ?: 0
    }

    override fun setPreviousTimerLengthSeconds(seconds: Long) {
        sharedPreferences.edit()
            ?.putLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, seconds)
            ?.apply()
    }

    override fun getTimerState(): TimerState {
        val ordinal = sharedPreferences.getInt(TIMER_STATE_ID, 0)
        return TimerState.values()[ordinal]
    }

    override fun setTimerState(state: TimerState) {
        val ordinal = state.ordinal
        sharedPreferences.edit()
            ?.putInt(TIMER_STATE_ID, ordinal)
            ?.apply()
    }

    override fun getSecondsRemaining(): Long {
        return sharedPreferences.getLong(SECONDS_REMAINING_ID, 0) ?: 0
    }

    override fun setSecondsRemaining(seconds: Long) {
        sharedPreferences.edit()
            ?.putLong(SECONDS_REMAINING_ID, seconds)
            ?.apply()
    }
}