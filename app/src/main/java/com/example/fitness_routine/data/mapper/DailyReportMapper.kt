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
    id = id,
    notes = notes.map { it.toDomain() },
    cardios = cardios.map { it.toDomain() },
    cheatMeals = cheatMeals.map { it.toDomain() },
    workout = workout?.toDomain(),
)



fun DailyReportDomainEntity.toData(): DailyReportDataEntity = DailyReportDataEntity(
    performedWorkout = performedWorkout,
    hadCreatine = hadCreatine,
    hadCheatMeal = hadCheatMeal,
    proteinGrams = proteinGrams,
    sleepQuality = sleepQuality,
    litersOfWater = litersOfWater,
    musclesTrained = musclesTrained,
    date = date,
    id = id,
    notes = notes.map { it.toData() },
    cardios = cardios.map { it.toData() },
    cheatMeals = cheatMeals.map { it.toData() },
    workout = workout?.toData(),
)
