package com.example.fitness_routine.domain.interactor.cheat


import com.example.fitness_routine.domain.SuspendUseCase
import com.example.fitness_routine.domain.entity.CardioDomainEntity
import com.example.fitness_routine.domain.entity.CheatMealDomainEntity
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.CheatMealRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


class UpdateCheatMeal @Inject constructor(
    private val cheatMealRepository: CheatMealRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, UpdateCheatMeal.Params>(dispatcher) {

    override suspend fun invoke(params: Params) {
        return cheatMealRepository.put(params.cheatMeal)
    }


    data class Params(val cheatMeal: CheatMealDomainEntity)
}