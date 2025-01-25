package com.example.fitness_routine.domain.entity

import com.example.fitness_routine.domain.entity.enums.Muscle


data class SetDomainEntity(
    val id: String,
    val userId: String,
    val date: Long,
    val muscle: Muscle,
    val exercise: String,
    val weight: String,
    val repeats: String,
)
