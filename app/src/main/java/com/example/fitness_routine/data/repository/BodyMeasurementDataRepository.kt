package com.example.fitness_routine.data.repository


import com.example.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import com.example.fitness_routine.domain.repository.BodyMeasurementRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class BodyMeasurementDataRepository(
    private val firestore: FirebaseFirestore
): BodyMeasurementRepository {
    override fun getBodyMeasurement(date: Long): Flow<BodyMeasurementDomainEntity?> {
    return flowOf(null)
    //return dataSource.getBodyMeasurement(date).map { it?.toDomain() }
    }

    override fun hasBodyMeasurement(date: Long): Flow<Boolean> {
        return flowOf(true)
    // return dataSource.hasBodyMeasurement(date)
    }

    override fun getBodyMeasurements(): Flow<List<BodyMeasurementDomainEntity>> {
        return flowOf()
    //        return dataSource.getBodyMeasurements()
//            .map { list ->
//                list.map { it.toDomain() }
//            }
    }

    override suspend fun put(measurement: BodyMeasurementDomainEntity) {
        //dataSource.put(measurement.toData())
    }

    override suspend fun update(measurement: BodyMeasurementDomainEntity) {
       // dataSource.update(measurement.toData())
    }

    override suspend fun delete(measurement: BodyMeasurementDomainEntity) {
       // dataSource.delete(measurement.toData())
    }
}


