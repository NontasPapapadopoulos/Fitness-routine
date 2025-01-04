package com.example.fitness_routine.domain.entity

import java.util.Date


data class NoteDomainEntity(
    val id: String,
    val date: Date,
    val text: String,
)
