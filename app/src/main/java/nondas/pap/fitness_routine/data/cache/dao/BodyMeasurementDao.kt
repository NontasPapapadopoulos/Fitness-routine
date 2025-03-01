package com.example.fitness_routine.data.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitness_routine.data.entity.BodyMeasurementDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BodyMeasurementDao {

    @Query("SELECT * FROM bodymeasurement WHERE date = :date LIMIT 1")
    fun getBodyMeasurementFlow(date: Long): Flow<BodyMeasurementDataEntity?>

    @Query("SELECT * FROM bodymeasurement")
    fun getBodyMeasurementFlow(): Flow<List<BodyMeasurementDataEntity>>

    @Query("SELECT COUNT(*) FROM bodymeasurement WHERE date = :date")
    fun getNumberOfMeasurements(date: Long): Int

    @Query("SELECT COUNT(*) > 0 FROM bodymeasurement WHERE date = :date")
    fun hasBodyMeasurement(date: Long): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun put(bodyMeasurement: BodyMeasurementDataEntity)

    @Delete
    suspend fun delete(bodyMeasurement: BodyMeasurementDataEntity)



    @Query("""
        UPDATE BodyMeasurement 
        SET 
            date = :date,
            weight = :weight,
            fat = :fat,
            muscleMass = :muscleMass,
            bmi = :bmi,
            tbw = :tbw,
            bmr = :bmr,
            visceralFat = :visceralFat,
            metabolicAge = :metabolicAge
        WHERE id = :id
    """)
    fun update(
        id: String,
        date: Long,
        weight: String,
        fat: String,
        muscleMass: String,
        bmi: String,
        tbw: String,
        bmr: String,
        visceralFat: String,
        metabolicAge: String
    )
}