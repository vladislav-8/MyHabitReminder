package com.practicum.myhabitreminder.common.di

import com.practicum.myhabitreminder.data.network.JokesApi
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


}