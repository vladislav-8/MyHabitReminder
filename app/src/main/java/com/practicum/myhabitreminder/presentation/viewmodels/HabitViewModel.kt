package com.practicum.myhabitreminder.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.myhabitreminder.domain.models.Habit
import com.practicum.myhabitreminder.domain.usecase.HabitRepository
import com.practicum.myhabitreminder.presentation.models.HabitState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitViewModel(
    private val habitRepository: HabitRepository
) : ViewModel() {

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
}