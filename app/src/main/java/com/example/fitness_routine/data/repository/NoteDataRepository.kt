package com.example.fitness_routine.data.repository

import com.example.fitness_routine.data.datasource.CheatMealDataSource
import com.example.fitness_routine.data.datasource.NoteDataSource
import com.example.fitness_routine.data.mapper.toData
import com.example.fitness_routine.data.mapper.toDomain
import com.example.fitness_routine.domain.entity.CheatMealDomainEntity
import com.example.fitness_routine.domain.entity.NoteDomainEntity
import com.example.fitness_routine.domain.repository.CheatMealRepository
import com.example.fitness_routine.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteDataRepository @Inject constructor(
    private val noteDataSource: NoteDataSource
): NoteRepository {
    override fun getNotes(date: Long): Flow<List<NoteDomainEntity>> {
        return noteDataSource.getNotes(date)
            .map { list -> list.map { it.toDomain() } }
    }

    override fun getNotes(): Flow<List<NoteDomainEntity>> {
        return noteDataSource.getNotes().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun put(note: NoteDomainEntity) {
        noteDataSource.put(note.toData())
    }

    override suspend fun delete(note: NoteDomainEntity) {
        noteDataSource.delete(note.toData())
    }

    override suspend fun init(date: Long) {
        noteDataSource.init(date)
    }


}