package com.example.fitness_routine.data.mapper

import com.example.fitness_routine.data.entity.BreakDataEntity
import com.example.fitness_routine.domain.entity.BreakDomainEntity


fun BreakDataEntity.toDomain(): BreakDomainEntity = BreakDomainEntity(
    date = date,
    index = index,
    muscle = muscle,
    exercise = exercise,
    time = time
)


fun BreakDomainEntity.toData(): BreakDataEntity = BreakDataEntity(
    date = date,
    index = index,
    muscle = muscle,
    exercise = exercise,
    time = time
)