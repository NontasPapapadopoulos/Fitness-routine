package com.example.fitness_routine.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fitness_routine.domain.entity.enums.Muscle


data class ExerciseDataEntity(
    val id: String,
    val name: String,
    val muscle: Muscle,
)
