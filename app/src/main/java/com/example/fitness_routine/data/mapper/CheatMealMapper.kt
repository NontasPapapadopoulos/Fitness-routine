package com.example.fitness_routine.data.mapper

import com.example.fitness_routine.data.entity.CheatMealDataEntity
import com.example.fitness_routine.data.util.toDate
import com.example.fitness_routine.domain.entity.CheatMealDomainEntity
import com.example.fitness_routine.domain.toTimeStamp


fun CheatMealDataEntity.toDomain(): CheatMealDomainEntity = CheatMealDomainEntity(
    id = id,
    date = date.toDate(),
    meal = meal

)


fun CheatMealDomainEntity.toData(): CheatMealDataEntity = CheatMealDataEntity(
    id = id,
    date = date.toTimeStamp(),
    meal = meal
)