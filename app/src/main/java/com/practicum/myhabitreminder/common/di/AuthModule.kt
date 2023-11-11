package com.practicum.myhabitreminder.common.di

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.practicum.myhabitreminder.MainActivityViewModel
import com.practicum.myhabitreminder.data.impl.AuthRepositoryImpl
import com.practicum.myhabitreminder.domain.repository.AuthRepository
import com.practicum.myhabitreminder.domain.usecase.firebase.GetAuthStateUseCase
import com.practicum.myhabitreminder.domain.usecase.firebase.LogOutUseCase
import com.practicum.myhabitreminder.domain.usecase.firebase.ResetPasswordUseCase
import com.practicum.myhabitreminder.domain.usecase.firebase.SignInUseCase
import com.practicum.myhabitreminder.domain.usecase.firebase.SignUpUseCase
import com.practicum.myhabitreminder.presentation.viewmodels.AuthorizationViewModel
import com.practicum.myhabitreminder.presentation.viewmodels.RegistrationViewModel
import com.practicum.myhabitreminder.presentation.viewmodels.ResetPassViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {

    viewModel<AuthorizationViewModel>() {
        AuthorizationViewModel(signIn = get())
    }

    viewModel<RegistrationViewModel> {
        RegistrationViewModel(signUp = get())
    }

    viewModel<ResetPassViewModel>() {
        ResetPassViewModel(resetPassword = get())
    }

    viewModel<MainActivityViewModel>() {
        MainActivityViewModel(getAuthStateUseCase = get())
    }

    factory<SignInUseCase> {
        SignInUseCase(repository = get())
    }

    factory<SignUpUseCase> {
        SignUpUseCase(repository = get())
    }

    factory<LogOutUseCase> {
        LogOutUseCase(repository = get())
    }

    single<AuthRepository> {
        AuthRepositoryImpl(authClient = get())
    }

    factory<GetAuthStateUseCase> {
        GetAuthStateUseCase(authRepository = get())
    }

    factory<ResetPasswordUseCase> {
        ResetPasswordUseCase(repository = get())
    }

    factory<LogOutUseCase> {
        LogOutUseCase(repository = get())
    }

    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }
}