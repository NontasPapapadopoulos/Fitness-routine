package com.example.fitness_routine.data.entity

import androidx.compose.runtime.Composable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Settings")
data class SettingsDataEntity(
    @PrimaryKey
    val id: Long = 0,
    val choice: String,
    @ColumnInfo(defaultValue = "")
    val isDarkModeEnabled: Boolean,
    @ColumnInfo(defaultValue = "60")
    val breakDuration: String
)
