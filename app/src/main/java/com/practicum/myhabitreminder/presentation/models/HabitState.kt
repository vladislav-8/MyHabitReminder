package com.practicum.myhabitreminder.presentation.models

import com.practicum.myhabitreminder.domain.models.Habit

sealed interface HabitState {

    data class Content(
        val habits: List<Habit>
    ) : HabitState

    object Empty : HabitState
}