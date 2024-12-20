package com.example.fitness_routine.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "BodyMeasurement"
)
data class BodyMeasurementDataEntity(
    @PrimaryKey
    val id: String,
    val date: Long,
    val weight: Float,
    val fat: Float,
    val muscleMass: Float,
    val bmi: Float,
    val tbw: Float,
    val bmr: Float,
    val visceralFat: Int,
    val metabolicAge: Int
)