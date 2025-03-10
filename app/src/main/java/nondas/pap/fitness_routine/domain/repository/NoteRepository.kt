package nondas.pap.fitness_routine.domain.repository

import nondas.pap.fitness_routine.domain.entity.NoteDomainEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(date: Long): Flow<List<NoteDomainEntity>>
    fun getNotes(): Flow<List<NoteDomainEntity>>
    suspend fun put(note: NoteDomainEntity)
    suspend fun delete(note: NoteDomainEntity)
    suspend fun update(note: NoteDomainEntity)
    suspend fun init(date: Long)
}