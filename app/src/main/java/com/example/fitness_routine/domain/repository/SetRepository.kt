package com.example.fitness_routine.domain.repository

import com.example.fitness_routine.data.entity.SetDataEntity
import com.example.fitness_routine.domain.entity.SetDomainEntity
import com.example.fitness_routine.domain.entity.WorkoutDomainEntity
import kotlinx.coroutines.flow.Flow

interface SetRepository {

    fun getSets(date: Long): Flow<List<SetDomainEntity>>
    fun getAllSets(): Flow<List<SetDomainEntity>>

    suspend fun addSet(set: SetDomainEntity)

    suspend fun deleteSet(set: SetDomainEntity)

    suspend fun updateSet(set: SetDomainEntity)
}