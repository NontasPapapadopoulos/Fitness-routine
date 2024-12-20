package com.example.fitness_routine.domain.interactor.cheat

import com.example.fitness_routine.domain.FlowUseCase
import com.example.fitness_routine.domain.entity.CardioDomainEntity
import com.example.fitness_routine.domain.entity.CheatMealDomainEntity
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.CardioRepository
import com.example.fitness_routine.domain.repository.CheatMealRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetCheatMeals @Inject constructor(
    private val cheatMealRepository: CheatMealRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<List<CheatMealDomainEntity>, GetCheatMeals.Params>(dispatcher) {


    override fun invoke(params: Params): Flow<List<CheatMealDomainEntity>> {
        return cheatMealRepository.getCheatMeals(params.date)
    }

    data class Params(val date: Long)

}