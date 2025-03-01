package nondas.pap.fitness_routine.domain.interactor.set


import nondas.pap.fitness_routine.domain.SuspendUseCase
import nondas.pap.fitness_routine.domain.entity.SetDomainEntity
import nondas.pap.fitness_routine.domain.executor.IoDispatcher
import nondas.pap.fitness_routine.domain.repository.SetRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


class DeleteSet @Inject constructor(
    private val setRepository: SetRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, DeleteSet.Params>(dispatcher) {

    override suspend fun invoke(params: Params) {
        return setRepository.delete(params.set)
    }

    data class Params(
        val set: SetDomainEntity
    )
}