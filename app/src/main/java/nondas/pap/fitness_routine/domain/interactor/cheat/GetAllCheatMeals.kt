package nondas.pap.fitness_routine.domain.interactor.cheat

import nondas.pap.fitness_routine.domain.FlowUseCase
import nondas.pap.fitness_routine.domain.entity.CardioDomainEntity
import nondas.pap.fitness_routine.domain.entity.CheatMealDomainEntity
import nondas.pap.fitness_routine.domain.executor.IoDispatcher
import nondas.pap.fitness_routine.domain.repository.CardioRepository
import nondas.pap.fitness_routine.domain.repository.CheatMealRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


open class GetAllCheatMeals @Inject constructor(
    private val cheatMealRepository: CheatMealRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<List<CheatMealDomainEntity>, Unit>(dispatcher) {


    override fun invoke(params: Unit): Flow<List<CheatMealDomainEntity>> {
        return cheatMealRepository.getCheatMeals()
    }


}