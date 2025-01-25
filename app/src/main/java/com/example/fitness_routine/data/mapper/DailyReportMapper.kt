package com.example.fitness_routine.data.mapper

import com.example.fitness_routine.data.util.convertToString
import com.example.fitness_routine.data.entity.DailyReportDataEntity
import com.example.fitness_routine.data.util.toDate
import com.example.fitness_routine.data.util.toList
import com.example.fitness_routine.data.util.toTimeStamp

import com.example.fitness_routine.domain.entity.DailyReportDomainEntity


fun DailyReportDataEntity.toDomain(): DailyReportDomainEntity = DailyReportDomainEntity(
    performedWorkout = performedWorkout,
    hadCreatine = hadCreatine,
    hadCheatMeal = hadCheatMeal,
    proteinGrams = proteinGrams,
    sleepQuality = sleepQuality,
    litersOfWater = litersOfWater,
    musclesTrained = musclesTrained.toList(),
    date = date,
)



fun DailyReportDomainEntity.toData(): DailyReportDataEntity = DailyReportDataEntity(
    performedWorkout = performedWorkout,
    hadCreatine = hadCreatine,
    hadCheatMeal = hadCheatMeal,
    proteinGrams = proteinGrams,
    sleepQuality = sleepQuality,
    litersOfWater = litersOfWater,
    musclesTrained = musclesTrained.convertToString(),
    date = date,
    id = id
)
