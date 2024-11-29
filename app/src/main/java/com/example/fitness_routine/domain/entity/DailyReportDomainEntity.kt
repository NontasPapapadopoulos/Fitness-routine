package com.example.fitness_routine.domain.entity

import com.example.fitness_routine.domain.entity.enums.Cardio
import java.util.Date

data class DailyReportDomainEntity(
    val date: Date,
    val performedWorkout: Boolean,
    val hadCreatine: Boolean,
    val hadCheatMeal: Boolean,
    val proteinGrams: String,
    val gymNotes: String,
    val sleepQuality: String, // from 1 to 5
    val litersOfWater: String,
    val musclesTrained: List<String>,
    val meal: String,
)

