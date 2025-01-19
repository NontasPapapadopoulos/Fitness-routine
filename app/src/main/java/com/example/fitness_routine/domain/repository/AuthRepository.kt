package com.example.fitness_routine.domain.repository

import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(authCredential: AuthCredential)
    suspend fun logout()
    suspend fun delete()
    suspend fun skipLogin()
    suspend fun getSkipLogin(): Boolean
    fun hasUserLoggedInFlow(): Flow<Boolean>
    suspend fun hasUserLoggedIn(): Boolean

}