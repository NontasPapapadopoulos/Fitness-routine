package com.example.fitness_routine.domain.entity

import java.util.Date


data class CheatMealDomainEntity(
    val id: String,
    val date: Date,
    val text: String,
)
