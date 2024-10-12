package com.example.fitness_routine.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.Date


@Entity(
    tableName = "DailyReport",
)
data class DailyReportDataEntity(
    @PrimaryKey
    val date: Long,
    val performedWorkout: Boolean,
    val hadCreatine: Boolean,
    val hadCheatMeal: Boolean,
    val proteinGrams: String,
    val cardioMinutes: String,
    val gymNotes: String,
    val sleepQuality: String, // from 1 to 5
    val litersOfWater: String,
    val musclesTrained: String
)


