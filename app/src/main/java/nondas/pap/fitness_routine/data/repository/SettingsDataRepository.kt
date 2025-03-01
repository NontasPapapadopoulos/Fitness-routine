package nondas.pap.fitness_routine.data.repository

import nondas.pap.fitness_routine.data.datasource.SettingsDataSource
import nondas.pap.fitness_routine.data.mapper.toData
import nondas.pap.fitness_routine.data.mapper.toDomain
import nondas.pap.fitness_routine.domain.entity.SettingsDomainEntity
import nondas.pap.fitness_routine.domain.entity.enums.Choice
import nondas.pap.fitness_routine.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsDataRepository @Inject constructor(
    private val settingsDataSource: SettingsDataSource
): SettingsRepository {

    override fun getSettings(): Flow<SettingsDomainEntity?> {
        return settingsDataSource.getSettings().map { it?.toDomain() }
    }

    override suspend fun changeSettings(settings: SettingsDomainEntity) {
        settingsDataSource.changeSettings(settings.toData())
    }

    override suspend fun changeChoice(choice: Choice) {
        settingsDataSource.changeChoice(choice)
    }

    override suspend fun init() {
        settingsDataSource.init()
    }


}