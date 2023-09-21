package com.practicum.myhabitreminder.di

import androidx.room.Room
import com.practicum.myhabitreminder.db.database.HabitDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {

    single {
        Room.databaseBuilder(androidContext(), HabitDatabase::class.java, "database.db")
            .build()
    }
}