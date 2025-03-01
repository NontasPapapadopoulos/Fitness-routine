package com.example.fitness_routine.domain.repository

import com.example.fitness_routine.domain.entity.SettingsDomainEntity
import com.example.fitness_routine.domain.entity.enums.Choice
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSettings(): Flow<SettingsDomainEntity?>
    suspend fun changeSettings(settings: SettingsDomainEntity)
    suspend fun changeChoice(choice: Choice)
    suspend fun init()
}