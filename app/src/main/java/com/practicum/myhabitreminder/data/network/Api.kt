package com.practicum.myhabitreminder.data.network

import com.practicum.myhabitreminder.data.network.models.Joke
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("jokes/random")
    fun getRandomJoke(@Query("category") category: String): Call<Joke>
}