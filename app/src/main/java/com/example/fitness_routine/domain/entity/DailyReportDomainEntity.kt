package com.example.fitness_routine.domain.entity

import com.example.fitness_routine.data.entity.CardioDataEntity
import com.example.fitness_routine.data.entity.CheatMealDataEntity
import com.example.fitness_routine.data.entity.NoteDataEntity
import com.example.fitness_routine.data.entity.WorkoutDataEntity
import com.example.fitness_routine.domain.entity.enums.Cardio
import java.util.Date

data class DailyReportDomainEntity(
    val id: String,
    val userId: String,
    val date: Date,
    val performedWorkout: Boolean,
    val hadCreatine: Boolean,
    val hadCheatMeal: Boolean,
    val proteinGrams: String,
    val sleepQuality: String,
    val litersOfWater: String,
    val musclesTrained: List<String>,
//    val cardios: List<CardioDomainEntity>,
//    val workout: WorkoutDomainEntity?,
//    val notes: List<NoteDomainEntity>,
//    val cheatMeals: List<CheatMealDomainEntity>
)

