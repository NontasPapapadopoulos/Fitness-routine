package com.example.fitness_routine.data.datasource

import com.example.fitness_routine.data.cache.dao.SetDao
import com.example.fitness_routine.data.entity.SetDataEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SetDataSource {
    fun getSets(date: Long): Flow<List<SetDataEntity>>
    fun getAllSets(): Flow<List<SetDataEntity>>

    suspend fun add(set: SetDataEntity)

    suspend fun delete(set: SetDataEntity)

    suspend fun update(set: SetDataEntity)
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

    override suspend fun add(set: SetDataEntity) {
        setDao.put(set)
    }

    override suspend fun delete(set: SetDataEntity) {
        setDao.delete(set)
    }

    override suspend fun update(set: SetDataEntity) {
        setDao.update(set.id, set.weight, set.repeats)
    }

}