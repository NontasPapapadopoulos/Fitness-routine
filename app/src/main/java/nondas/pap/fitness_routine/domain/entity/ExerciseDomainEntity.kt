package com.example.fitness_routine.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fitness_routine.domain.entity.enums.Muscle


data class ExerciseDomainEntity(
    val name: String,
    val muscle: Muscle,
  //  val timeBreak: Int
//    val index: Int
)
