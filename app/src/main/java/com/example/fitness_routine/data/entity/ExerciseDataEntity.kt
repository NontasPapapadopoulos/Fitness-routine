package com.example.fitness_routine.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fitness_routine.domain.entity.enums.Muscle


@Entity(tableName = "Exercise")
data class ExerciseDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val muscle: Muscle,
//    val index: Int
)
