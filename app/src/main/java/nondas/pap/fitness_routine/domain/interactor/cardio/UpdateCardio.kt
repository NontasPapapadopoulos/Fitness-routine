package nondas.pap.fitness_routine.domain.interactor.cardio


import nondas.pap.fitness_routine.domain.SuspendUseCase
import nondas.pap.fitness_routine.domain.entity.CardioDomainEntity
import nondas.pap.fitness_routine.domain.executor.IoDispatcher
import nondas.pap.fitness_routine.domain.repository.CardioRepository
import kotlinx.coroutines.CoroutineDispatcher
import java.util.UUID
import javax.inject.Inject


open class UpdateCardio @Inject constructor(
    private val cardioRepository: CardioRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, UpdateCardio.Params>(dispatcher) {

    override suspend fun invoke(params: Params) {
        return cardioRepository.update(params.cardio)
    }


    data class Params(val cardio: CardioDomainEntity)
}