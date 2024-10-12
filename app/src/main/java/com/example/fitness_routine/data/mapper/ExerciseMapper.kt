package com.example.fitness_routine.data.mapper

import com.example.fitness_routine.data.entity.ExerciseDataEntity
import com.example.fitness_routine.domain.entity.ExerciseDomainEntity


fun ExerciseDataEntity.toDomain(): ExerciseDomainEntity = ExerciseDomainEntity(
    id = id,
    name = name,
    muscle = muscle
)


fun ExerciseDomainEntity.toData(): ExerciseDataEntity = ExerciseDataEntity(
    id = id,
    name = name,
    muscle = muscle
)