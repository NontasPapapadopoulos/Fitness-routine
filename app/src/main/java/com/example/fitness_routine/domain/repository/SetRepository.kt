package com.example.fitness_routine.domain.repository

import com.example.fitness_routine.domain.entity.SetDomainEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface SetRepository {
    fun getSets(userId: String, date: Date): Flow<List<SetDomainEntity>>
    fun getAllSets(userId: String): Flow<List<SetDomainEntity>>
    suspend fun add(set: SetDomainEntity)
    suspend fun delete(set: SetDomainEntity)
    suspend fun update(set: SetDomainEntity)
}