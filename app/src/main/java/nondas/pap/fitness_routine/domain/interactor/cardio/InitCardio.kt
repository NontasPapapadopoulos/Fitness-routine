package nondas.pap.fitness_routine.domain.interactor.cardio


import nondas.pap.fitness_routine.domain.SuspendUseCase
import nondas.pap.fitness_routine.domain.entity.DailyReportDomainEntity
import nondas.pap.fitness_routine.domain.executor.IoDispatcher
import nondas.pap.fitness_routine.domain.repository.CardioRepository
import nondas.pap.fitness_routine.domain.repository.DailyRoutineRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


open class InitCardio @Inject constructor(
    private val cardioRepository: CardioRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, InitCardio.Params>(dispatcher) {


    override suspend fun invoke(params: Params) {
        return cardioRepository.init(params.date)
    }


    data class Params(val date: Long)
}