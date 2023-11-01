package com.practicum.myhabitreminder.domain.repository

import com.practicum.myhabitreminder.common.utils.TaskResult

interface AuthRepository {

    suspend fun signUp(email: String, password: String): TaskResult<Boolean>

    suspend fun signIn(email: String, password: String): TaskResult<Boolean>
}