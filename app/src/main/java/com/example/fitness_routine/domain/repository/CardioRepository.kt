package com.example.fitness_routine.domain.repository

import com.example.fitness_routine.domain.entity.CardioDomainEntity
import kotlinx.coroutines.flow.Flow

interface CardioRepository {
    fun getCardios(date: Long): Flow<List<CardioDomainEntity>>
    fun getCardios(): Flow<List<CardioDomainEntity>>
    suspend fun update(cardio: CardioDomainEntity)
    suspend fun put(cardio: CardioDomainEntity)
    suspend fun delete(cardio: CardioDomainEntity)
    suspend fun init(date: Long)
}