package com.practicum.myhabitreminder.common.di

import android.content.Context
import android.content.SharedPreferences
import com.practicum.myhabitreminder.common.utils.TIMER_STATE_ID
import com.practicum.myhabitreminder.data.impl.HabitRepositoryImpl
import com.practicum.myhabitreminder.data.impl.StorageRepositoryImpl
import com.practicum.myhabitreminder.domain.repository.HabitRepository
import com.practicum.myhabitreminder.domain.repository.StorageRepository
import com.practicum.myhabitreminder.domain.usecase.timer.GetAlarmSetTimeUseCase
import com.practicum.myhabitreminder.domain.usecase.timer.GetPreviousTimerLengthSecondsUseCase
import com.practicum.myhabitreminder.domain.usecase.timer.GetSecondsRemainingUseCase
import com.practicum.myhabitreminder.domain.usecase.timer.GetTimerStateUseCase
import com.practicum.myhabitreminder.domain.usecase.timer.SetPreviousTimerLengthSecondsUseCase
import com.practicum.myhabitreminder.domain.usecase.timer.GetTimerLengthUseCase
import com.practicum.myhabitreminder.domain.usecase.timer.SetAlarmSetTimeUseCase
import com.practicum.myhabitreminder.domain.usecase.timer.SetSecondsRemainingUseCase
import com.practicum.myhabitreminder.domain.usecase.timer.SetTimerStateUseCase
import com.practicum.myhabitreminder.presentation.viewmodels.HabitViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val habitModule = module {

    single<HabitRepository> {
        HabitRepositoryImpl(database = get(), converter = get())
    }

    viewModelOf(::HabitViewModel)

    singleOf(::GetPreviousTimerLengthSecondsUseCase)
    singleOf(::GetSecondsRemainingUseCase)
    singleOf(::GetTimerStateUseCase)
    singleOf(::GetTimerLengthUseCase)
    singleOf(::SetPreviousTimerLengthSecondsUseCase)
    singleOf(::SetSecondsRemainingUseCase)
    singleOf(::SetTimerStateUseCase)
    singleOf(::StorageRepositoryImpl) bind StorageRepository::class

    factory<GetAlarmSetTimeUseCase> {
        GetAlarmSetTimeUseCase(repository = get())
    }

    factory<SetAlarmSetTimeUseCase> {
        SetAlarmSetTimeUseCase(repository = get())
    }

    single<SharedPreferences> {
        androidContext().getSharedPreferences(TIMER_STATE_ID, Context.MODE_PRIVATE)
    }
}