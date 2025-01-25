package com.example.fitness_routine.data.repository

import com.example.fitness_routine.data.datasource.SettingsDataSource
import com.example.fitness_routine.data.mapper.toData
import com.example.fitness_routine.data.mapper.toDomain
import com.example.fitness_routine.domain.entity.SettingsDomainEntity
import com.example.fitness_routine.domain.entity.enums.Choice
import com.example.fitness_routine.domain.repository.SettingsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsDataRepository @Inject constructor(
    private val firestore: FirebaseFirestore
): SettingsRepository {

    override fun getSettings(): Flow<SettingsDomainEntity?> {
        return flowOf()
//        return settingsDataSource.getSettings().map { it?.toDomain() }
    }

    override suspend fun changeSettings(settings: SettingsDomainEntity) {
//        settingsDataSource.changeSettings(settings.toData())
    }

    override suspend fun changeChoice(choice: Choice) {
//        settingsDataSource.changeChoice(choice)
    }

    override suspend fun init() {
//        settingsDataSource.init()
    }


}