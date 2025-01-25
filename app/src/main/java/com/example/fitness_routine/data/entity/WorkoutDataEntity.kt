package com.example.fitness_routine.data.entity


import com.example.fitness_routine.domain.entity.enums.Muscle
import java.time.LocalDate
import java.util.Date


data class WorkoutDataEntity(
    val id: String,
    val date: Date,
    val muscles: List<Muscle>,
    val sets: List<SetDataEntity>
)


