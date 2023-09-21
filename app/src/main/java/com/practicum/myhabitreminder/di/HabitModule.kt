package com.practicum.myhabitreminder.di


import com.practicum.myhabitreminder.data.impl.HabitRepositoryImpl
import com.practicum.myhabitreminder.domain.usecase.HabitRepository
import org.koin.dsl.module

val habitModule = module {

    single<HabitRepository> {
        HabitRepositoryImpl(database = get(), converter = get())
    }
}