package com.example.fitness_routine.domain.entity

import com.example.fitness_routine.data.entity.SetDataEntity
import com.example.fitness_routine.domain.entity.enums.Muscle
import java.util.Date

data class WorkoutDomainEntity(
    val id: String,
    val date: Date,
    val muscles: List<Muscle>,
    val sets: List<SetDomainEntity>
)
