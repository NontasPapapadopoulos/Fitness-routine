package com.example.fitness_routine.domain.interactor.settings

import com.example.fitness_routine.domain.FlowUseCase
import com.example.fitness_routine.domain.entity.SettingsDomainEntity
import com.example.fitness_routine.domain.entity.getDefaultSettings
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetSettings @Inject constructor(
    private val settingsRepository: SettingsRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<SettingsDomainEntity, Unit>(dispatcher) {


    override fun invoke(params: Unit): Flow<SettingsDomainEntity> {
        return  settingsRepository.getSettings().map { it?: getDefaultSettings() }
    }


}