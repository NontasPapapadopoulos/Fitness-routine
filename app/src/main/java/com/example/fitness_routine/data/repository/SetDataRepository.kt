package com.example.fitness_routine.data.repository

import com.example.fitness_routine.data.datasource.SetDataSource
import com.example.fitness_routine.data.mapper.toData
import com.example.fitness_routine.data.mapper.toDomain
import com.example.fitness_routine.domain.entity.SetDomainEntity
import com.example.fitness_routine.domain.repository.SetRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class SetDataRepository(
    private val firestore: FirebaseFirestore
): SetRepository {
    override fun getSets(date: Long): Flow<List<SetDomainEntity>> {
        return flowOf()
//        return setDataSource.getSets(date)
//            .map { sets -> sets.map { it.toDomain() } }
    }

    override fun getAllSets(): Flow<List<SetDomainEntity>> {
        return flowOf()
//        return setDataSource.getAllSets()
//            .map { sets -> sets.map { it.toDomain() } }
    }


    override suspend fun add(set: SetDomainEntity) {
//        setDataSource.add(set.toData())
    }

    override suspend fun delete(set: SetDomainEntity) {
//        setDataSource.delete(set.toData())
    }

    override suspend fun update(set: SetDomainEntity) {
//        setDataSource.update(set.toData())
    }

}