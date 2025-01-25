package com.example.fitness_routine.data.entity

import androidx.compose.runtime.Composable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class SettingsDataEntity(
    val id: String,
    val userId: String,
    val choice: String,
    val isDarkModeEnabled: Boolean,
    val breakDuration: String
)
