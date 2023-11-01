package com.practicum.myhabitreminder.data.impl

import com.google.firebase.auth.FirebaseAuth
import com.practicum.myhabitreminder.common.utils.ErrorType
import com.practicum.myhabitreminder.common.utils.TaskResult
import com.practicum.myhabitreminder.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(private val authClient: FirebaseAuth) : AuthRepository {

    override suspend fun signUp(email: String, password: String): TaskResult<Boolean> {
        if (authClient.currentUser != null) return TaskResult.Error(ErrorType.AlreadySignedIn)

        return try {
            withContext(Dispatchers.IO) {
                authClient.createUserWithEmailAndPassword(email, password).await()
                TaskResult.Success(true)
            }
        } catch (e: Exception) {
            TaskResult.Error(getAuthError(e.message))
        }
    }

    override suspend fun signIn(email: String, password: String): TaskResult<Boolean> {
        if (authClient.currentUser != null) return TaskResult.Error(ErrorType.AlreadySignedIn)

        return try {
            withContext(Dispatchers.IO) {
                authClient.signInWithEmailAndPassword(email, password).await()
                TaskResult.Success(true)
            }
        } catch (e: Exception) {
            TaskResult.Error(getAuthError(e.message))
        }
    }

    private fun getAuthError(message: String?): ErrorType {
        return message?.let {
            ErrorType.AuthFailed(message)
        } ?: ErrorType.Unknown
    }
}