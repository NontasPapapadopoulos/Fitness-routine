package com.example.fitness_routine.data.entity


import com.example.fitness_routine.domain.entity.enums.Muscle



data class SetDataEntity(
    val id: String,
    val workoutDate: Long,
    val muscle: Muscle,
    val exercise: String,
    val weight: String,
    val repeats: String,
)
