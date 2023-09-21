package com.practicum.myhabitreminder.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.myhabitreminder.db.dao.HabitDao
import com.practicum.myhabitreminder.db.entity.HabitEntity

@Database(
    entities = [HabitEntity::class],
    version = 1
)
abstract class HabitDatabase: RoomDatabase() {

    abstract fun habitDao(): HabitDao
}