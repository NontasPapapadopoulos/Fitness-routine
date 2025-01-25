package com.example.fitness_routine.domain.repository

import com.example.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface BodyMeasurementRepository {
    fun getBodyMeasurement(userId: String, date: Date): Flow<BodyMeasurementDomainEntity?>
    fun getBodyMeasurements(userId: String): Flow<List<BodyMeasurementDomainEntity>>
    suspend fun put(measurement: BodyMeasurementDomainEntity)
    suspend fun update(measurement: BodyMeasurementDomainEntity)
    suspend fun delete(measurement: BodyMeasurementDomainEntity)
    fun hasBodyMeasurement(userId:String, date: Date): Flow<Boolean>
}