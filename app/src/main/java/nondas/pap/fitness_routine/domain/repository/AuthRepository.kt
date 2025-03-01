package com.example.fitness_routine.domain.repository

interface AuthRepository {

    suspend fun login()
    suspend fun logout()
    suspend fun register()
    suspend fun delete()

    
}