package com.example.fitness_routine.domain.entity

import java.util.Date

data class BodyMeasurementDomainEntity(
    val id: String,
    val userId: String,
    val date: Date,
    val weight: String,
    val fat: String,
    val muscleMass: String,
    val bmi: String,
    val tbw: String,
    val bmr: String,
    val visceralFat: String,
    val metabolicAge: String
)