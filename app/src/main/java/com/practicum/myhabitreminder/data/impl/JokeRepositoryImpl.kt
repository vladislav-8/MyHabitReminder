package com.practicum.myhabitreminder.data.impl

import com.practicum.myhabitreminder.data.network.JokeResponse
import com.practicum.myhabitreminder.data.network.JokesApi
import com.practicum.myhabitreminder.domain.repository.JokeRepository

class JokeRepositoryImpl(private val api: JokesApi): JokeRepository {

    override suspend fun getJoke(): JokeResponse {
        return api.getRandomJoke()
    }
}