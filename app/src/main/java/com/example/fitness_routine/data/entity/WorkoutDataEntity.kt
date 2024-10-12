package com.example.fitness_routine.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fitness_routine.domain.entity.enums.Muscle


@Entity(
    tableName = "Workout"
)
data class WorkoutDataEntity(
    @PrimaryKey
    val date: Long,
    val muscles: List<Muscle>,

)


