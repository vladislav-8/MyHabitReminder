package com.practicum.myhabitreminder.common.di

import com.google.firebase.auth.FirebaseAuth
import com.practicum.myhabitreminder.data.impl.AuthRepositoryImpl
import com.practicum.myhabitreminder.domain.repository.AuthRepository
import com.practicum.myhabitreminder.domain.usecase.firebase.SignInUseCase
import com.practicum.myhabitreminder.domain.usecase.firebase.SignUpUseCase
import com.practicum.myhabitreminder.presentation.viewmodels.AuthorizationViewModel
import com.practicum.myhabitreminder.presentation.viewmodels.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {

    viewModel<AuthorizationViewModel>() {
        AuthorizationViewModel(signIn = get())
    }

    viewModel<RegistrationViewModel> {
        RegistrationViewModel(signUp = get())
    }

    factory<SignInUseCase> {
        SignInUseCase(repository = get())
    }

    factory<SignUpUseCase> {
        SignUpUseCase(repository = get())
    }

    single<AuthRepository> {
        AuthRepositoryImpl(authClient = get())
    }

    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }
}