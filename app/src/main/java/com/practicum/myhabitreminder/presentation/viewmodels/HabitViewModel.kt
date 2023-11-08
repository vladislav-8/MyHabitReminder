package com.practicum.myhabitreminder.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.myhabitreminder.domain.models.Habit
import com.practicum.myhabitreminder.domain.repository.HabitRepository
import com.practicum.myhabitreminder.domain.usecase.timer.GetPreviousTimerLengthSecondsUseCase
import com.practicum.myhabitreminder.domain.usecase.timer.GetSecondsRemainingUseCase
import com.practicum.myhabitreminder.domain.usecase.timer.GetTimerLengthUseCase
import com.practicum.myhabitreminder.domain.usecase.timer.GetTimerStateUseCase
import com.practicum.myhabitreminder.domain.usecase.timer.SetPreviousTimerLengthSecondsUseCase
import com.practicum.myhabitreminder.domain.usecase.timer.SetSecondsRemainingUseCase
import com.practicum.myhabitreminder.domain.usecase.timer.SetTimerStateUseCase
import com.practicum.myhabitreminder.presentation.models.HabitState
import com.practicum.myhabitreminder.presentation.models.TimerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitViewModel(
    private val habitRepository: HabitRepository,
    private val getTimerLengthUseCase: GetTimerLengthUseCase,
    private val getPreviousTimerLengthSeconds: GetPreviousTimerLengthSecondsUseCase,
    private val setSecondsRemainingUseCase: SetSecondsRemainingUseCase,
    private val setTimerStateUseCase: SetTimerStateUseCase,
    private val setPreviousTimerLengthSeconds: SetPreviousTimerLengthSecondsUseCase,
    private val getTimerState: GetTimerStateUseCase,
    private val getSecondsRemaining: GetSecondsRemainingUseCase
) : ViewModel() {

    var timerLengthSeconds = 0L


    var title = ""
    var description = ""
    var timeStamp = ""
    var daysCounter = 0

    var day = 0
    var month = 0
    var year = 0
    var cleanDate = ""

    private val stateLiveData = MutableLiveData<HabitState>()
    fun observeState(): LiveData<HabitState> = stateLiveData

    fun getAllHabits() {
        viewModelScope.launch {
            habitRepository
                .getAllHabits()
                .collect { habits ->
                    if (habits.isEmpty()) {
                        renderState(HabitState.Empty)
                    } else {
                        renderState(HabitState.Content(habits))
                    }
                }
        }
    }

    private fun renderState(state: HabitState) {
        stateLiveData.postValue(state)
    }


    fun addHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            habitRepository.addHabit(habit)
        }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            habitRepository.deleteHabit(habit)
        }
    }

    fun updateHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            habitRepository.updateHabits(habit)
        }
    }

    fun deleteAllHabits() {
        viewModelScope.launch(Dispatchers.IO) {
            habitRepository.deleteAllHabits()
        }
    }

    fun setNewTimerLength() {
        val lengthInMinutes = getTimerLengthUseCase.invoke()
        timerLengthSeconds = (lengthInMinutes * 1800L)
    }


    fun setPreviousTimerLength() {
        timerLengthSeconds = getPreviousTimerLengthSeconds.invoke()
    }

    fun setSecondsRemaining(seconds: Long) {
        setSecondsRemainingUseCase.invoke(seconds)
    }

    fun setTimerState(state: TimerState) {
        setTimerStateUseCase.invoke(state)
    }

    fun setPreviousTimerLengthSeconds(seconds: Long) {
        setPreviousTimerLengthSeconds.invoke(seconds)
    }

    fun getTimerState(): TimerState {
       return getTimerState.invoke()
    }

    fun getSecondsRemaining(): Long {
       return getSecondsRemaining.invoke()
    }
}