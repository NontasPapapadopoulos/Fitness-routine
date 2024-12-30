package com.example.fitness_routine.data.datasource

import com.example.fitness_routine.data.cache.dao.SetDao
import com.example.fitness_routine.data.entity.SetDataEntity
import com.example.fitness_routine.domain.entity.SetDomainEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SetDataSource {
    fun getSets(date: Long): Flow<List<SetDataEntity>>
    fun getAllSets(): Flow<List<SetDataEntity>>

    suspend fun addSet(set: SetDataEntity)

    suspend fun deleteSet(set: SetDataEntity)

    suspend fun updateSet(set: SetDataEntity)
}


class SetDataSourceImpl @Inject constructor(
    private val setDao: SetDao
): SetDataSource {
    override fun getSets(date: Long): Flow<List<SetDataEntity>> {
        return setDao.getSets(date)
    }

    override fun getAllSets(): Flow<List<SetDataEntity>> {
        return setDao.getAllSets()
    }

    override suspend fun addSet(set: SetDataEntity) {
        setDao.put(set)
    }

    override suspend fun deleteSet(set: SetDataEntity) {
        setDao.delete(set)
    }

    override suspend fun updateSet(set: SetDataEntity) {
        setDao.update(set)
    }

}