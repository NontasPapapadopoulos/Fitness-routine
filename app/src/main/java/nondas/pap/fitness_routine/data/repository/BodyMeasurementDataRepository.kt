package com.example.fitness_routine.data.repository

import com.example.fitness_routine.data.datasource.BodyMeasurementDataSource
import com.example.fitness_routine.data.mapper.toData
import com.example.fitness_routine.data.mapper.toDomain
import com.example.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import com.example.fitness_routine.domain.repository.BodyMeasurementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BodyMeasurementDataRepository(
    private val dataSource: BodyMeasurementDataSource
): BodyMeasurementRepository {
    override fun getBodyMeasurement(date: Long): Flow<BodyMeasurementDomainEntity?> {
        return dataSource.getBodyMeasurement(date).map { it?.toDomain() }
    }

    override fun hasBodyMeasurement(date: Long): Flow<Boolean> {
        return dataSource.hasBodyMeasurement(date)
    }

    override fun getBodyMeasurements(): Flow<List<BodyMeasurementDomainEntity>> {
        return dataSource.getBodyMeasurements()
            .map { list ->
                list.map { it.toDomain() }
            }
    }

    override suspend fun put(measurement: BodyMeasurementDomainEntity) {
        dataSource.put(measurement.toData())
    }

    override suspend fun update(measurement: BodyMeasurementDomainEntity) {
        dataSource.update(measurement.toData())
    }

    override suspend fun delete(measurement: BodyMeasurementDomainEntity) {
        dataSource.delete(measurement.toData())
    }
}


