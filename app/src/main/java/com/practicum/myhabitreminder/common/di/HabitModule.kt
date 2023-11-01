package com.practicum.myhabitreminder.common.di


import com.practicum.myhabitreminder.data.impl.HabitRepositoryImpl
import com.practicum.myhabitreminder.domain.repository.HabitRepository
import com.practicum.myhabitreminder.presentation.viewmodels.HabitViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val habitModule = module {

    single<HabitRepository> {
        HabitRepositoryImpl(database = get(), converter = get())
    }

    viewModel {
        HabitViewModel(habitRepository = get())
    }
}