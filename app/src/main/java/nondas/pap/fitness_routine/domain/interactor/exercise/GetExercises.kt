package nondas.pap.fitness_routine.domain.interactor.exercise

import nondas.pap.fitness_routine.domain.FlowUseCase
import nondas.pap.fitness_routine.domain.entity.CheatMealDomainEntity
import nondas.pap.fitness_routine.domain.entity.DailyReportDomainEntity
import nondas.pap.fitness_routine.domain.entity.ExerciseDomainEntity
import nondas.pap.fitness_routine.domain.executor.IoDispatcher
import nondas.pap.fitness_routine.domain.repository.DailyRoutineRepository
import nondas.pap.fitness_routine.domain.repository.ExerciseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetExercises @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<List<ExerciseDomainEntity>, Unit>(dispatcher) {


    override fun invoke(params: Unit): Flow<List<ExerciseDomainEntity>> {
        return exerciseRepository.getExercises()

    }
}