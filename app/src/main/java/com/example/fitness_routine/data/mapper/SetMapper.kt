package com.example.fitness_routine.data.mapper

import com.example.fitness_routine.data.entity.SetDataEntity
import com.example.fitness_routine.domain.entity.SetDomainEntity


fun SetDomainEntity.toData(): SetDataEntity = SetDataEntity(
    id = id,
    workoutDate = date,
    repeats = repeats,
    weight = weight,
    muscle = muscle,
    exercise = exercise
)


fun SetDataEntity.toDomain(): SetDomainEntity = SetDomainEntity(
    id = id,
    date = workoutDate,
    repeats = repeats,
    weight = weight,
    muscle = muscle,
    exercise = exercise
)