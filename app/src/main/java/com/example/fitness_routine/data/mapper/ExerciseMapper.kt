package com.example.fitness_routine.data.mapper

import com.example.fitness_routine.data.entity.ExerciseDataEntity
import com.example.fitness_routine.domain.entity.ExerciseDomainEntity


fun ExerciseDataEntity.toDomain(): ExerciseDomainEntity = ExerciseDomainEntity(
    name = name,
    muscle = muscle
)


fun ExerciseDomainEntity.toData(): ExerciseDataEntity = ExerciseDataEntity(
    name = name,
    muscle = muscle
)