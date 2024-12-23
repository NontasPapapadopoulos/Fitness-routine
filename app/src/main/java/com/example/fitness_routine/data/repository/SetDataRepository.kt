package com.example.fitness_routine.data.repository

import com.example.fitness_routine.data.datasource.SetDataSource
import com.example.fitness_routine.data.mapper.toData
import com.example.fitness_routine.data.mapper.toDomain
import com.example.fitness_routine.domain.entity.SetDomainEntity
import com.example.fitness_routine.domain.repository.SetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SetDataRepository(
    private val setDataSource: SetDataSource
): SetRepository {
    override fun getSets(date: Long): Flow<List<SetDomainEntity>> {
        return setDataSource.getSets(date)
            .map { sets -> sets.map { it.toDomain() } }
    }

    override fun getAllSets(): Flow<List<SetDomainEntity>> {
        return setDataSource.getAllSets()
            .map { sets -> sets.map { it.toDomain() } }
    }


    override suspend fun addSet(set: SetDomainEntity) {
        setDataSource.addSet(set.toData())
    }

    override suspend fun deleteSet(set: SetDomainEntity) {
        setDataSource.deleteSet(set.toData())
    }

    override suspend fun updateSet(set: SetDomainEntity) {
        setDataSource.updateSet(set.toData())
    }

}