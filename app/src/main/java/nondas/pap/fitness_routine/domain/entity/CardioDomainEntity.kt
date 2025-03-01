package com.example.fitness_routine.domain.entity

import java.util.Date

data class CardioDomainEntity(
    val id: String,
    val date: Date,
    val type: String,
    val minutes: String
)
