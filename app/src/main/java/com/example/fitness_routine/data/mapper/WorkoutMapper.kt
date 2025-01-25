package com.example.fitness_routine.data.mapper

import com.example.fitness_routine.data.entity.WorkoutDataEntity
import com.example.fitness_routine.domain.entity.WorkoutDomainEntity


fun WorkoutDomainEntity.toData(): WorkoutDataEntity = WorkoutDataEntity(
    date = date,
    muscles = muscles,
    id = id,
    userId = userId
)


fun WorkoutDataEntity.toDomain(): WorkoutDomainEntity = WorkoutDomainEntity(
    date = date,
    muscles = muscles,
    id = id,
    userId = userId
)