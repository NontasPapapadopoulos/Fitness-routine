package com.example.fitness_routine.domain.repository

import com.example.fitness_routine.domain.entity.NoteDomainEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(date: Long): Flow<List<NoteDomainEntity>>
    fun getNotes(): Flow<List<NoteDomainEntity>>
    suspend fun put(cheatMeal: NoteDomainEntity)
    suspend fun delete(cheatMeal: NoteDomainEntity)
    suspend fun init(date: Long)
}