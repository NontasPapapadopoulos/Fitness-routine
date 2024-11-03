package com.example.fitness_routine.data.repository

import com.example.fitness_routine.data.datasource.SettingsDataSource
import com.example.fitness_routine.data.mapper.toDomain
import com.example.fitness_routine.domain.entity.SettingsDomainEntity
import com.example.fitness_routine.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsDataRepository @Inject constructor(
    private val settingsDataSource: SettingsDataSource
): SettingsRepository {

    override fun getSettings(): Flow<SettingsDomainEntity> {
        return settingsDataSource.getSettings().map { it.toDomain() }
    }
}