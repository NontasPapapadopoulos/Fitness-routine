package nondas.pap.fitness_routine.domain.interactor.set

import nondas.pap.fitness_routine.domain.FlowUseCase
import nondas.pap.fitness_routine.domain.entity.CheatMealDomainEntity
import nondas.pap.fitness_routine.domain.entity.DailyReportDomainEntity
import nondas.pap.fitness_routine.domain.entity.ExerciseDomainEntity
import nondas.pap.fitness_routine.domain.entity.SetDomainEntity
import nondas.pap.fitness_routine.domain.executor.IoDispatcher
import nondas.pap.fitness_routine.domain.repository.DailyRoutineRepository
import nondas.pap.fitness_routine.domain.repository.ExerciseRepository
import nondas.pap.fitness_routine.domain.repository.SetRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetSets @Inject constructor(
    private val setRepository: SetRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<List<SetDomainEntity>, GetSets.Params>(dispatcher) {


    override fun invoke(params: Params): Flow<List<SetDomainEntity>> {
        return setRepository.getSets(params.date)

    }

    data class Params(val date: Long)
}