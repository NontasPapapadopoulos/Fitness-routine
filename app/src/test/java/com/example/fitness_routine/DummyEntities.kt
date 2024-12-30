package com.example.fitness_routine

import com.example.fitness_routine.domain.entity.CheatMealDomainEntity
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.ExerciseDomainEntity
import com.example.fitness_routine.domain.entity.SetDomainEntity
import com.example.fitness_routine.domain.entity.SettingsDomainEntity
import com.example.fitness_routine.domain.entity.WorkoutDomainEntity
import com.example.fitness_routine.domain.entity.enums.Muscle
import java.util.Date

object DummyEntities

val DummyEntities.exercise: ExerciseDomainEntity
    get() = ExerciseDomainEntity(
        name = "",
        muscle = Muscle.Chest
    )


val DummyEntities.dailyReport: DailyReportDomainEntity
    get() = DailyReportDomainEntity(
        date = Date(),
        performedWorkout = false,
        hadCreatine = false,
        hadCheatMeal = false,
        proteinGrams = "",
        cardioMinutes = "",
        gymNotes = "",
        sleepQuality = "",
        litersOfWater = "",
        musclesTrained = listOf()
    )

val DummyEntities.workout: WorkoutDomainEntity
    get() = WorkoutDomainEntity(
        date = 0,
        muscles = listOf()
    )

val DummyEntities.cheatMeal: CheatMealDomainEntity
    get() = CheatMealDomainEntity(
        date = Date()
    )


val DummyEntities.set: SetDomainEntity
    get() = SetDomainEntity(
        id = 0,
        date = 0,
        muscle = Muscle.Chest,
        exercise = "",
        weight = 0,
        repeats = 0
    )


val DummyEntities.settings: SettingsDomainEntity
    get() = SettingsDomainEntity(
        isDarkModeEnabled = true,
        breakDuration = "",
        choice = ""
    )