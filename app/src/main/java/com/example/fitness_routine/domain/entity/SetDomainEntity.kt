package com.example.fitness_routine.domain.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.fitness_routine.domain.entity.enums.Muscle


data class SetDomainEntity(
    val id: String,
    val workoutDate: Long,
    val muscle: Muscle,
    val exercise: String,
    val weight: String,
    val repeats: String,
)
