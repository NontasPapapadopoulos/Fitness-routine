package com.example.fitness_routine.domain.interactor.settings

import com.example.fitness_routine.domain.FlowUseCase
import com.example.fitness_routine.domain.SuspendUseCase
import com.example.fitness_routine.domain.entity.SettingsDomainEntity
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject


class GetSettings @Inject constructor(
    private val settingsRepository: SettingsRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<SettingsDomainEntity, Unit>(dispatcher) {


    override fun invoke(params: Unit): Flow<SettingsDomainEntity> {
        return  settingsRepository.getSettings()
            .filterNotNull()
    }


}