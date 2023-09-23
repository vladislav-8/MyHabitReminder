package com.practicum.myhabitreminder.data.network

import com.practicum.myhabitreminder.data.network.model.Joke
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiCall {

    fun getRandomJoke(category:String, callback: (Joke?) -> Unit) {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://official-joke-api.appspot.com/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create an instance of the ChuckNorrisApi interface
        // using the Retrofit instance
        val api = retrofit.create(Api::class.java)

        api.getRandomJoke(category).enqueue(object : Callback<Joke> {
            // it define the code that execute if response is received
            override fun onResponse(call: Call<Joke>, response: Response<Joke>) {

                if (response.isSuccessful) {
                    val joke: Joke? = response.body()
                    callback(joke)
                }
                // If the response is not successful,
                // pass null to the callback function
                else {
                    callback(null)
                }
            }

            // when the API call fails
            override fun onFailure(call: Call<Joke>, t: Throwable) {
                // Pass null to the callback function
                callback(null)
            }
        })
    }
}
