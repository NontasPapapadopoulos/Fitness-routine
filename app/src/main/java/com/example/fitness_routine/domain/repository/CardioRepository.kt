package com.example.fitness_routine.domain.repository

import com.example.fitness_routine.domain.entity.CardioDomainEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface CardioRepository {
    fun getCardios(userId: String, date: Date): Flow<List<CardioDomainEntity>>
    fun getCardios(userId: String): Flow<List<CardioDomainEntity>>
    suspend fun update(cardio: CardioDomainEntity)
    suspend fun put(cardio: CardioDomainEntity)
    suspend fun delete(cardio: CardioDomainEntity)
    suspend fun init(userId: String, date: Date)
}