package com.example.fitness_routine.domain.entity

data class BodyMeasurementDomainEntity(
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