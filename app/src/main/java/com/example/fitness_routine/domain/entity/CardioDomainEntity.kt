package com.example.fitness_routine.domain.entity

import java.time.LocalDate
import java.util.Date

data class CardioDomainEntity(
    val id: String,
    val userId: String,
    val date: Date,
    val type: String,
    val minutes: String
)
