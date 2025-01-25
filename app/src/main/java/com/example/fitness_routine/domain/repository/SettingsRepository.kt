package com.example.fitness_routine.domain.repository

import com.example.fitness_routine.domain.entity.SettingsDomainEntity
import com.example.fitness_routine.domain.entity.enums.Choice
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSettings(userId: String): Flow<SettingsDomainEntity?>
    suspend fun changeSettings(userId: String, settings: SettingsDomainEntity)
    suspend fun changeChoice(userId: String, choice: Choice)
    suspend fun init(userId: String)
}