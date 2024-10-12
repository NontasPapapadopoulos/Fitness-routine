package com.example.fitness_routine.data.mapper

import com.example.fitness_routine.data.entity.WorkoutWithSetsDataEntity
import com.example.fitness_routine.domain.entity.WorkoutWithSetsDomainEntity

fun WorkoutWithSetsDomainEntity.toData(): WorkoutWithSetsDataEntity = WorkoutWithSetsDataEntity(
    workout = workout.toData(),
    sets = sets.map { it.toData() }
)


fun WorkoutWithSetsDataEntity.toDomain(): WorkoutWithSetsDomainEntity = WorkoutWithSetsDomainEntity(
    workout = workout.toDomain(),
    sets = sets.map { it.toDomain() }
)