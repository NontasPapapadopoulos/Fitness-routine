package nondas.pap.fitness_routine.domain.interactor.set


import nondas.pap.fitness_routine.domain.SuspendUseCase
import nondas.pap.fitness_routine.domain.entity.SetDomainEntity
import nondas.pap.fitness_routine.domain.entity.enums.Muscle
import nondas.pap.fitness_routine.domain.executor.IoDispatcher
import nondas.pap.fitness_routine.domain.repository.SetRepository
import kotlinx.coroutines.CoroutineDispatcher
import java.util.UUID
import javax.inject.Inject


class CreateNewSet @Inject constructor(
    private val setRepository: SetRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, CreateNewSet.Params>(dispatcher) {


    override suspend fun invoke(params: Params) {

        val emptySet = SetDomainEntity(
            date = params.workoutDate,
            muscle = params.muscle,
            exercise = params.exercise,
            weight = "",
            repeats = "",
            id = UUID.randomUUID().toString()
        )
        return setRepository.add(emptySet)
    }


    data class Params(
        val exercise: String,
        val muscle: Muscle,
        val workoutDate: Long
    )
}