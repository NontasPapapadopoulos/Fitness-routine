package com.example.fitness_routine.data.entity


import java.time.LocalDate
import java.util.Date


data class BodyMeasurementDataEntity(
    val id: String,
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