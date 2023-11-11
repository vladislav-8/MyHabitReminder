package com.practicum.myhabitreminder.domain.repository

import com.practicum.myhabitreminder.presentation.models.TimerState

interface StorageRepository {

    fun getTimerLength(): Int

    fun getPreviousTimerLengthSeconds(): Long

    fun setPreviousTimerLengthSeconds(seconds: Long)

    fun getTimerState(): TimerState

    fun setTimerState(state: TimerState)

    fun getSecondsRemaining(): Long

    fun setSecondsRemaining(seconds: Long)
}