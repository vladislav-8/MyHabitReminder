package com.practicum.myhabitreminder.domain.repository

import com.practicum.myhabitreminder.data.network.JokeResponse

interface JokeRepository {

    suspend fun getJoke(): JokeResponse
}