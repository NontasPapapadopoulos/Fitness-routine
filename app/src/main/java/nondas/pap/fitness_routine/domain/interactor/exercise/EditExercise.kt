package nondas.pap.fitness_routine.domain.interactor.exercise


import nondas.pap.fitness_routine.domain.SuspendUseCase
import nondas.pap.fitness_routine.domain.entity.DailyReportDomainEntity
import nondas.pap.fitness_routine.domain.entity.ExerciseDomainEntity
import nondas.pap.fitness_routine.domain.executor.IoDispatcher
import nondas.pap.fitness_routine.domain.repository.DailyRoutineRepository
import nondas.pap.fitness_routine.domain.repository.ExerciseRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


class EditExercise @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, EditExercise.Params>(dispatcher) {


    override suspend fun invoke(params: Params) {
        return exerciseRepository.edit(params.oldName, params.newName)
    }


    data class Params(val oldName: String, val newName: String)
}