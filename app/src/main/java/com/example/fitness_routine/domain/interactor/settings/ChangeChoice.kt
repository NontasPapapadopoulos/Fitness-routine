package com.example.fitness_routine.domain.interactor.settings

import com.example.fitness_routine.domain.SuspendUseCase
import com.example.fitness_routine.domain.entity.SettingsDomainEntity
import com.example.fitness_routine.domain.entity.enums.Choice
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

open class ChangeChoice @Inject constructor(
    private val settingsRepository: SettingsRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, ChangeChoice.Params>(dispatcher) {

    override suspend fun invoke(params: Params) {
        settingsRepository.changeChoice(params.choice)
    }



    data class Params(val choice: Choice)


}