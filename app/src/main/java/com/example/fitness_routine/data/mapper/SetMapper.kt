package com.example.fitness_routine.data.mapper

import com.example.fitness_routine.data.entity.SetDataEntity
import com.example.fitness_routine.domain.entity.SetDomainEntity


fun SetDomainEntity.toData(): SetDataEntity = SetDataEntity(
    id = id,
    workoutDate = workoutDate,
    repeats = repeats,
    weight = weight,
    muscle = muscle,
    exercise = exercise
)


fun SetDataEntity.toDomain(): SetDomainEntity = SetDomainEntity(
    id = id,
    workoutDate = workoutDate,
    repeats = repeats,
    weight = weight,
    muscle = muscle,
    exercise = exercise
)