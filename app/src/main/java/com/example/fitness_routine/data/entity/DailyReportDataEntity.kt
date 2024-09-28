package com.example.fitness_routine.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(
    tableName = "DailyReport",

)
data class DailyReportDataEntity(
    @PrimaryKey
    val date: Date,
    val performedWorkout: Boolean,
    val hadCreatine: Boolean,
    val hadCheatMeal: Boolean,
    val proteinGrams: String,
    val gymNotes: String,
    val sleepQuality: Int, // from 1 to 5
    val litersOfWater: Int,
    val musclesTrained: List<String>
)
