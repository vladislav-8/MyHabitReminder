package com.practicum.myhabitreminder.domain.repository

import com.practicum.myhabitreminder.domain.models.Habit
import kotlinx.coroutines.flow.Flow

interface HabitRepository {

    suspend fun addHabit(habit: Habit)

    suspend fun deleteHabit(habit: Habit)

    suspend fun deleteAllHabits()

    suspend fun getAllHabits(): Flow<List<Habit>>

    suspend fun updateHabits(habit: Habit)
}