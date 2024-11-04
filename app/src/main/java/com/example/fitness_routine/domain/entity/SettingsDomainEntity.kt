package com.example.fitness_routine.domain.entity

import androidx.compose.runtime.Composable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fitness_routine.domain.entity.enums.Choice

data class SettingsDomainEntity(
    val choice: String,
    val isDarkModeEnabled: Boolean,
    val breakDuration: String
)


fun getDefaultSettings() = SettingsDomainEntity(
    choice = Choice.Workout.name,
    isDarkModeEnabled = true,
    breakDuration = "60"
)