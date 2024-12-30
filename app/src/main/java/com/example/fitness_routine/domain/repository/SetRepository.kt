package com.example.fitness_routine.domain.repository

import com.example.fitness_routine.domain.entity.SetDomainEntity
import kotlinx.coroutines.flow.Flow

interface SetRepository {

    fun getSets(date: Long): Flow<List<SetDomainEntity>>
    fun getAllSets(): Flow<List<SetDomainEntity>>

    suspend fun add(set: SetDomainEntity)

    suspend fun delete(set: SetDomainEntity)

    suspend fun update(set: SetDomainEntity)
}