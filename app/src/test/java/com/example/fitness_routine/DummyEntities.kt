package com.example.fitness_routine

import android.provider.ContactsContract.CommonDataKinds.Note
import com.example.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import com.example.fitness_routine.domain.entity.CardioDomainEntity
import com.example.fitness_routine.domain.entity.CheatMealDomainEntity
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.ExerciseDomainEntity
import com.example.fitness_routine.domain.entity.NoteDomainEntity
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
        date = Date(),
        id = "",
        text = ""
    )


val DummyEntities.set: SetDomainEntity
    get() = SetDomainEntity(
        id = "",
        date = 0,
        muscle = Muscle.Chest,
        exercise = "",
        weight = "",
        repeats = ""
    )


val DummyEntities.settings: SettingsDomainEntity
    get() = SettingsDomainEntity(
        isDarkModeEnabled = true,
        breakDuration = "",
        choice = ""
    )

val DummyEntities.note: NoteDomainEntity
    get() = NoteDomainEntity(
        id = "",
        text = "",
        date = Date()
    )

val DummyEntities.measurement: BodyMeasurementDomainEntity
    get() = BodyMeasurementDomainEntity(
        id = "",
        date = 0,
        weight = "",
        fat = "",
        metabolicAge = "",
        bmi = "",
        tbw = "",
        bmr = "",
        visceralFat = "",
        muscleMass = "",
    )

val DummyEntities.cardio: CardioDomainEntity
    get() = CardioDomainEntity(
        id = "",
        type = "",
        date = Date(),
        minutes = ""
    )