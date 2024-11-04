package com.example.fitness_routine.domain.repository

import com.example.fitness_routine.data.entity.SettingsDataEntity
import com.example.fitness_routine.domain.entity.SettingsDomainEntity
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSettings(): Flow<SettingsDomainEntity?>

    suspend fun changeSettings(settings: SettingsDomainEntity)
}