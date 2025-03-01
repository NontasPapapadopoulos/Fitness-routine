package com.example.fitness_routine.domain.entity


import com.example.fitness_routine.domain.entity.enums.Muscle


data class BreakDomainEntity(
    val date: Long,
    val index: String,
    val muscle: Muscle,
    val exercise: String,
    val time: Long

)
