package nondas.pap.fitness_routine.data.datasource

import nondas.pap.fitness_routine.data.entity.SettingsDataEntity
import nondas.pap.fitness_routine.domain.entity.enums.Choice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import nondas.pap.fitness_routine.data.cache.dao.SettingsDao
import javax.inject.Inject

interface SettingsDataSource {
    fun getSettings(): Flow<SettingsDataEntity?>
    suspend fun changeSettings(settings: SettingsDataEntity)
    suspend fun changeChoice(choice: Choice)
    suspend fun init()
}


class SettingsDataSourceImpl @Inject constructor(
    private val settingsDao: SettingsDao
): SettingsDataSource {

    override fun getSettings(): Flow<SettingsDataEntity?> {
       val settings = settingsDao.getSettings()
        return settingsDao.getSettings()
    }

    override suspend fun changeSettings(settings: SettingsDataEntity) {
        settingsDao.update(settings)
    }

    override suspend fun changeChoice(choice: Choice) {
        settingsDao.changeChoice(choice.name)
    }


    override suspend fun init() {
        if (!settingsDao.hasSettings()) {
            settingsDao.put(getDefaultSettings())
        }
    }


}

private fun getDefaultSettings() = SettingsDataEntity(
    choice = Choice.Workout.name,
    isDarkModeEnabled = true,
    breakDuration = "60"
)