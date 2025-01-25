package com.example.fitness_routine.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


data class CheatMealDataEntity(
    val id: String,
    val date: Long,
    val meal: String,
)
