package nondas.pap.fitness_routine.domain.interactor.cheat


import nondas.pap.fitness_routine.domain.SuspendUseCase
import nondas.pap.fitness_routine.domain.entity.CardioDomainEntity
import nondas.pap.fitness_routine.domain.entity.CheatMealDomainEntity
import nondas.pap.fitness_routine.domain.executor.IoDispatcher
import nondas.pap.fitness_routine.domain.repository.CheatMealRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


open class UpdateCheatMeal @Inject constructor(
    private val cheatMealRepository: CheatMealRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, UpdateCheatMeal.Params>(dispatcher) {

    override suspend fun invoke(params: Params) {
        return cheatMealRepository.update(params.cheatMeal)
    }


    data class Params(val cheatMeal: CheatMealDomainEntity)
}