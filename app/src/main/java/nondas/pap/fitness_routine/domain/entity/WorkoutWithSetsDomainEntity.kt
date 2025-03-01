package com.example.fitness_routine.domain.entity

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutWithSetsDomainEntity(
    val workout: WorkoutDomainEntity,
    val sets: List<SetDomainEntity>
)