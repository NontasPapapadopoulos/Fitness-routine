package com.example.fitness_routine.domain.entity



data class SettingsDomainEntity(
    val id: String,
    val choice: String,
    val isDarkModeEnabled: Boolean,
    val breakDuration: String
)


