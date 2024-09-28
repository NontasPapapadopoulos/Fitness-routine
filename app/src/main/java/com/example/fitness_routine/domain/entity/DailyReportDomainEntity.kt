package com.example.fitness_routine.domain.entity

data class DailyReportDomainEntity(
    val performedWorkout: Boolean,
    val hadCreatine: Boolean,
    val hadCheatMeal: Boolean,
    val proteinGrams: String,
    val gymNotes: String,
    val sleepQuality: Int, // from 1 to 5
    val litersOfWater: Int,
    val musclesTrained: List<String>
)
