package com.example.fitness_routine.data.mapper

import com.example.fitness_routine.data.convertToString
import com.example.fitness_routine.data.entity.DailyReportDataEntity
import com.example.fitness_routine.data.toDate
import com.example.fitness_routine.data.toList
import com.example.fitness_routine.data.toTimeStamp

import com.example.fitness_routine.domain.entity.DailyReportDomainEntity


fun DailyReportDataEntity.toDomain(): DailyReportDomainEntity = DailyReportDomainEntity(
    performedWorkout = performedWorkout,
    hadCreatine = hadCreatine,
    hadCheatMeal = hadCheatMeal,
    proteinGrams = proteinGrams,
    gymNotes = gymNotes,
    sleepQuality = sleepQuality,
    litersOfWater = litersOfWater,
    musclesTrained = musclesTrained.toList(),
    cardioMinutes = cardioMinutes,
    date = date.toDate()
)



fun DailyReportDomainEntity.toData(): DailyReportDataEntity = DailyReportDataEntity(
    performedWorkout = performedWorkout,
    hadCreatine = hadCreatine,
    hadCheatMeal = hadCheatMeal,
    proteinGrams = proteinGrams,
    gymNotes = gymNotes,
    sleepQuality = sleepQuality,
    litersOfWater = litersOfWater,
    musclesTrained = musclesTrained.convertToString(),
    cardioMinutes = cardioMinutes,
    date = date.toTimeStamp()
)
