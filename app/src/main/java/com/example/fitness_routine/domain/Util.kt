package com.example.fitness_routine.domain

import com.example.fitness_routine.domain.entity.enums.Muscle
import java.util.Date


fun List<String>.toMuscles(): List<Muscle> = if (this.isEmpty()) listOf() else this.map { Muscle.valueOf(it) }

//fun List<Muscle>.toMuscleList

fun Date.toTimeStamp(): Long = this.time
