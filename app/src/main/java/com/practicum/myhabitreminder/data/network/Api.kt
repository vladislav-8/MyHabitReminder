package com.practicum.myhabitreminder.data.network

import com.practicum.myhabitreminder.data.network.model.Joke
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("jokes/random")
    fun getRandomJoke(@Query("category") category: String): Call<Joke>
}