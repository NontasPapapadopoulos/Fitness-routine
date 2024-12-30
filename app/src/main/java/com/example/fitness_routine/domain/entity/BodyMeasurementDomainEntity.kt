package com.example.fitness_routine.domain.entity

data class BodyMeasurementDomainEntity(
    val id: String,
    val date: Long,
    val weight: String,
    val fat: String,
    val muscleMass: String,
    val bmi: String,
    val tbw: String,
    val bmr: String,
    val visceralFat: String,
    val metabolicAge: String
)