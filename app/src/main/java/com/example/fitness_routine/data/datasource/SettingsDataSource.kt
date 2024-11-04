package com.example.fitness_routine.data.datasource

import com.example.fitness_routine.data.cache.dao.SettingsDao
import com.example.fitness_routine.data.entity.SettingsDataEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SettingsDataSource {
    fun getSettings(): Flow<SettingsDataEntity?>

    suspend fun changeSettings(settings: SettingsDataEntity)
}



class SettingsDataSourceImpl @Inject constructor(
    private val settingsDao: SettingsDao
): SettingsDataSource {

    override fun getSettings(): Flow<SettingsDataEntity?> {
        return settingsDao.getSettings()
    }

    override suspend fun changeSettings(settings: SettingsDataEntity) {
        settingsDao.put(settings)
    }


}