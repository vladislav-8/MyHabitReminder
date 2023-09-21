package com.practicum.myhabitreminder.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.practicum.myhabitreminder.db.entity.HabitEntity

@Dao
interface HabitDao {

    @Insert(entity = HabitEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHabit(habitEntity: HabitEntity)

    @Update
    suspend fun updateHabit(habitEntity: HabitEntity)

    @Delete
    suspend fun deleteHabit(habitEntity: HabitEntity)

    @Query("SELECT * FROM ${HabitEntity.TABLE_NAME} ORDER BY id DESC")
    suspend fun getAllHabits(): List<HabitEntity>

    @Query("DELETE FROM ${HabitEntity.TABLE_NAME}")
    suspend fun deleteAllHabits()
}