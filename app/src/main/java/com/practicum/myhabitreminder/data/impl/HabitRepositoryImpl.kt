package com.practicum.myhabitreminder.data.impl

import com.practicum.myhabitreminder.common.db.converters.ConverterDb
import com.practicum.myhabitreminder.common.db.database.HabitDatabase
import com.practicum.myhabitreminder.common.db.entity.HabitEntity
import com.practicum.myhabitreminder.domain.models.Habit
import com.practicum.myhabitreminder.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HabitRepositoryImpl(
    private val database: HabitDatabase,
    private val converter: ConverterDb,
    ): HabitRepository {

    override suspend fun addHabit(habit: Habit) {
        database.habitDao().addHabit(converter.mapFromHabitToHabitEntity(habit))
    }

    override suspend fun deleteHabit(habit: Habit) {
        database.habitDao().deleteHabit(converter.mapFromHabitToHabitEntity(habit))
    }

    override suspend fun deleteAllHabits() {
        database.habitDao().deleteAllHabits()
    }

    override suspend fun getAllHabits(): Flow<List<Habit>> = flow {
        val habits = database.habitDao().getAllHabits()
        emit(convertFromHabitEntity(habits))
    }

    override suspend fun updateHabits(habit: Habit) {
        database.habitDao().updateHabit(converter.mapFromHabitToHabitEntity(habit))
    }

    private fun convertFromHabitEntity(habits: List<HabitEntity>): List<Habit> {
        return habits.map { habits ->
            converter.mapFromHabitEntityToHabit(
                habits
            )
        }
    }
}