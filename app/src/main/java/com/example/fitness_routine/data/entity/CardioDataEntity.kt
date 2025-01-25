package com.example.fitness_routine.data.entity


import java.time.LocalDate
import java.util.Date


data class CardioDataEntity(
    val id: String,
    val date: Date,
    val type: String,
    val minutes: String
)
