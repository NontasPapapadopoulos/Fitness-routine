package com.example.fitness_routine.domain.repository

import com.example.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import kotlinx.coroutines.flow.Flow

interface BodyMeasurementRepository {
    fun getBodyMeasurement(date: Long): Flow<BodyMeasurementDomainEntity?>
    fun getBodyMeasurements(): Flow<List<BodyMeasurementDomainEntity>>
    suspend fun put(measurement: BodyMeasurementDomainEntity)
    suspend fun update(measurement: BodyMeasurementDomainEntity)
    suspend fun delete(measurement: BodyMeasurementDomainEntity)
    fun hasBodyMeasurement(date: Long): Flow<Boolean>
}