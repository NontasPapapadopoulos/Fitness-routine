package nondas.pap.fitness_routine.data.mapper

import nondas.pap.fitness_routine.data.entity.SettingsDataEntity
import nondas.pap.fitness_routine.domain.entity.SettingsDomainEntity


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