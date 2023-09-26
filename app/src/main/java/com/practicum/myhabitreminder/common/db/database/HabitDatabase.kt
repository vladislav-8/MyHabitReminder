package com.practicum.myhabitreminder.common.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.myhabitreminder.common.db.dao.HabitDao
import com.practicum.myhabitreminder.common.db.entity.HabitEntity

@Database(
    entities = [HabitEntity::class],
    version = 1
)
abstract class HabitDatabase: RoomDatabase() {

    abstract fun habitDao(): HabitDao
}