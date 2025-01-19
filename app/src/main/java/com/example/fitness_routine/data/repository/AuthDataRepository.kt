package com.example.fitness_routine.data.repository

import com.example.fitness_routine.data.datasource.AuthDataSource
import com.example.fitness_routine.domain.repository.AuthRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class AuthDataRepository @Inject constructor(
    private val authDataSource: AuthDataSource
): AuthRepository {
    override suspend fun login() {
        authDataSource.login()
    }

    override suspend fun logout() {
        authDataSource.logout()
    }


    override suspend fun delete() {
        authDataSource.delete()
    }


}