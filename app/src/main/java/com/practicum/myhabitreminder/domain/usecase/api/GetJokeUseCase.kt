package com.practicum.myhabitreminder.domain.usecase.api

import com.practicum.myhabitreminder.domain.repository.JokeRepository

class GetJokeUseCase(val repository: JokeRepository) {

    suspend operator fun invoke() = repository.getJoke()
}