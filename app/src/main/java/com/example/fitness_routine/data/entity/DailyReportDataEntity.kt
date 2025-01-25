package com.example.fitness_routine.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fitness_routine.domain.entity.enums.Cardio
import java.time.LocalDate
import java.util.Date



data class DailyReportDataEntity(
    val id: String,
    val date: Date,
    val performedWorkout: Boolean,
    val hadCreatine: Boolean,
    val hadCheatMeal: Boolean,
    val proteinGrams: String,
    val sleepQuality: String,
    val litersOfWater: String,
    val musclesTrained: String,
    val cardios: List<CardioDataEntity>,
    val workout: WorkoutDataEntity,
    val notes: List<NoteDataEntity>,
    val cheatMeals: List<CheatMealDataEntity>
)



