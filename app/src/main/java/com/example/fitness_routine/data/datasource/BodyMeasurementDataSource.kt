package com.example.fitness_routine.data.datasource

import com.example.fitness_routine.data.cache.dao.BodyMeasurementDao
import com.example.fitness_routine.data.cache.dao.CardioDao
import com.example.fitness_routine.data.cache.dao.NoteDao
import com.example.fitness_routine.data.entity.BodyMeasurementDataEntity
import com.example.fitness_routine.data.entity.CardioDataEntity
import com.example.fitness_routine.data.entity.NoteDataEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

interface BodyMeasurementDataSource {
    fun getBodyMeasurement(date: Long): Flow<BodyMeasurementDataEntity>
    fun getBodyMeasurements(): Flow<List<BodyMeasurementDataEntity>>
    suspend fun put(measurement: BodyMeasurementDataEntity)
    suspend fun delete(measurement: BodyMeasurementDataEntity)
//    suspend fun init(date: Long)
}


class BodyMeasurementDataSourceImpl @Inject constructor(
    private val bodyMeasurementDao: BodyMeasurementDao
): BodyMeasurementDataSource {

    override fun getBodyMeasurement(date: Long): Flow<BodyMeasurementDataEntity> {
        return bodyMeasurementDao.getBodyMeasurementFlow(date)
    }

    override fun getBodyMeasurements(): Flow<List<BodyMeasurementDataEntity>> {
        return bodyMeasurementDao.getBodyMeasurementFlow()
    }

    override suspend fun put(measurement: BodyMeasurementDataEntity) {
        bodyMeasurementDao.put(measurement)
    }

    override suspend fun delete(measurement: BodyMeasurementDataEntity) {
        bodyMeasurementDao.delete(measurement)
    }

//    override suspend fun init(date: Long) {
//
//    }

}