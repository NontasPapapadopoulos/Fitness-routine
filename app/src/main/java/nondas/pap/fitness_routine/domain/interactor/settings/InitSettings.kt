package nondas.pap.fitness_routine.domain.interactor.settings

import nondas.pap.fitness_routine.domain.SuspendUseCase
import nondas.pap.fitness_routine.domain.executor.IoDispatcher
import nondas.pap.fitness_routine.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


class InitSettings @Inject constructor(
    private val settingsRepository: SettingsRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, Unit>(dispatcher) {


    override suspend fun invoke(params: Unit) {
        return  settingsRepository.init()
    }


}