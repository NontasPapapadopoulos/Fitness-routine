package com.example.fitness_routine.data.mapper

import com.example.fitness_routine.data.entity.SettingsDataEntity
import com.example.fitness_routine.domain.entity.SettingsDomainEntity


fun SettingsDataEntity.toDomain(): SettingsDomainEntity = SettingsDomainEntity(
    choice = choice,
    isDarkModeEnabled = isDarkModeEnabled,
    breakDuration = breakDuration
)



fun SettingsDomainEntity.toData(): SettingsDataEntity = SettingsDataEntity(
    choice = choice,
    isDarkModeEnabled = isDarkModeEnabled,
    breakDuration = breakDuration
)