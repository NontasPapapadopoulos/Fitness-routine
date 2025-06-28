package nondas.pap.fitness_routine.domain.entity

import nondas.pap.fitness_routine.domain.entity.enums.Choice


data class SettingsDomainEntity(
    val choice: Choice,
    val isDarkModeEnabled: Boolean,
    val breakDuration: String
)


