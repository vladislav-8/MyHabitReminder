package com.practicum.myhabitreminder.common.di

import com.practicum.myhabitreminder.data.impl.AuthRepositoryImpl
import com.practicum.myhabitreminder.data.impl.JokeRepositoryImpl
import com.practicum.myhabitreminder.data.network.JokesApi
import com.practicum.myhabitreminder.domain.repository.AuthRepository
import com.practicum.myhabitreminder.domain.repository.JokeRepository
import com.practicum.myhabitreminder.domain.usecase.api.GetJokeUseCase
import com.practicum.myhabitreminder.domain.usecase.firebase.SignUpUseCase
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val ApiModule = module {

    single<JokesApi> {
        Retrofit.Builder().baseUrl("https://official-joke-api.appspot.com/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JokesApi::class.java)
    }


    single<JokeRepository> {
        JokeRepositoryImpl(api = get())
    }

    factory<GetJokeUseCase> {
        GetJokeUseCase(repository = get())
    }

}