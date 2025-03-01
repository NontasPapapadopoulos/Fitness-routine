package nondas.pap.fitness_routine.domain.interactor.cardio

import nondas.pap.fitness_routine.domain.FlowUseCase
import nondas.pap.fitness_routine.domain.entity.CardioDomainEntity
import nondas.pap.fitness_routine.domain.entity.CheatMealDomainEntity
import nondas.pap.fitness_routine.domain.entity.DailyReportDomainEntity
import nondas.pap.fitness_routine.domain.entity.ExerciseDomainEntity
import nondas.pap.fitness_routine.domain.executor.IoDispatcher
import nondas.pap.fitness_routine.domain.repository.CardioRepository
import nondas.pap.fitness_routine.domain.repository.DailyRoutineRepository
import nondas.pap.fitness_routine.domain.repository.ExerciseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject


open class GetCardios @Inject constructor(
    private val cardioRepository: CardioRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<List<CardioDomainEntity>, GetCardios.Params>(dispatcher) {


    override fun invoke(params: Params): Flow<List<CardioDomainEntity>> {
        return cardioRepository.getCardios(params.date)
    }

    data class Params(val date: Long)

}