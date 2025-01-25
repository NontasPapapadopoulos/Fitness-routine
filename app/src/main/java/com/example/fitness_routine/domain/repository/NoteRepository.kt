package com.example.fitness_routine.domain.repository

import com.example.fitness_routine.domain.entity.NoteDomainEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface NoteRepository {
    fun getNotes(userId: String, date: Date): Flow<List<NoteDomainEntity>>
    fun getNotes(userId: String): Flow<List<NoteDomainEntity>>
    suspend fun put(note: NoteDomainEntity)
    suspend fun delete(note: NoteDomainEntity)
    suspend fun update(note: NoteDomainEntity)
    suspend fun init(userId: String, date: Date)
}