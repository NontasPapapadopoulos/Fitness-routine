package nondas.pap.fitness_routine.data.mapper

import nondas.pap.fitness_routine.data.entity.SettingsDataEntity
import nondas.pap.fitness_routine.domain.entity.SettingsDomainEntity
import nondas.pap.fitness_routine.domain.entity.enums.Choice


fun SettingsDataEntity.toDomain(): SettingsDomainEntity = SettingsDomainEntity(
    choice = Choice.valueOf(choice),
    isDarkModeEnabled = isDarkModeEnabled,
    breakDuration = breakDuration
)



fun SettingsDomainEntity.toData(): SettingsDataEntity = SettingsDataEntity(
    choice = choice.name,
    isDarkModeEnabled = isDarkModeEnabled,
    breakDuration = breakDuration
)