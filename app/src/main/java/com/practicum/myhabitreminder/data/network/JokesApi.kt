package com.practicum.myhabitreminder.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface JokesApi {

    @GET("jokes/random")
    suspend fun getRandomJoke(): JokeResponse
}