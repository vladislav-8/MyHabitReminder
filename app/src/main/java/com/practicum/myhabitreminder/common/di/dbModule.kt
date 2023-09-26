package com.practicum.myhabitreminder.common.di

import androidx.room.Room
import com.practicum.myhabitreminder.common.db.converters.ConverterDb
import com.practicum.myhabitreminder.common.db.database.HabitDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {

    single {
        Room.databaseBuilder(androidContext(), HabitDatabase::class.java, "database.db")
            .build()
    }

    factory {
        ConverterDb()
    }
}