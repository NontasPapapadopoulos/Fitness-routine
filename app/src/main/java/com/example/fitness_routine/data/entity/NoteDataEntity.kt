package com.example.fitness_routine.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


data class NoteDataEntity(
    val id: String,
    val userId: String,
    val date: Long,
    val note: String
)
