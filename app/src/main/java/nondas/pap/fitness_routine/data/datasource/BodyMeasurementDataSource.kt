package nondas.pap.fitness_routine.data.datasource


import nondas.pap.fitness_routine.data.entity.BodyMeasurementDataEntity
import kotlinx.coroutines.flow.Flow
import nondas.pap.fitness_routine.data.cache.dao.BodyMeasurementDao
import javax.inject.Inject

interface BodyMeasurementDataSource {
    fun getBodyMeasurement(date: Long): Flow<BodyMeasurementDataEntity?>
    fun hasBodyMeasurement(date: Long): Flow<Boolean>
    fun getBodyMeasurements(): Flow<List<BodyMeasurementDataEntity>>
    suspend fun put(measurement: BodyMeasurementDataEntity)
    suspend fun update(measurement: BodyMeasurementDataEntity)
    suspend fun delete(measurement: BodyMeasurementDataEntity)
}


class BodyMeasurementDataSourceImpl @Inject constructor(
    private val bodyMeasurementDao: BodyMeasurementDao
): BodyMeasurementDataSource {

    override fun getBodyMeasurement(date: Long): Flow<BodyMeasurementDataEntity?> {
        return bodyMeasurementDao.getBodyMeasurementFlow(date)
    }

    override fun hasBodyMeasurement(date: Long): Flow<Boolean> {
        return bodyMeasurementDao.hasBodyMeasurement(date)
    }

    override fun getBodyMeasurements(): Flow<List<BodyMeasurementDataEntity>> {
        return bodyMeasurementDao.getBodyMeasurementFlow()
    }

    override suspend fun put(measurement: BodyMeasurementDataEntity) {
        bodyMeasurementDao.put(measurement)
    }

    override suspend fun update(measurement: BodyMeasurementDataEntity) {
        bodyMeasurementDao.update(
            id = measurement.id,
            date = measurement.reportDate,
            weight = measurement.weight,
            fat = measurement.fat,
            muscleMass = measurement.muscleMass,
            bmi = measurement.bmi,
            tbw = measurement.tbw,
            bmr = measurement.bmr,
            visceralFat = measurement.visceralFat,
            metabolicAge = measurement.visceralFat
        )
    }

    override suspend fun delete(measurement: BodyMeasurementDataEntity) {
        bodyMeasurementDao.delete(measurement)
    }

}