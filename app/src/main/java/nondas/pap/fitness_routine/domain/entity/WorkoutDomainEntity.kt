package com.example.fitness_routine.domain.entity

import com.example.fitness_routine.domain.entity.enums.Muscle
import java.util.Date

data class WorkoutDomainEntity(
    val date: Long,
    val muscles: List<Muscle>
)
