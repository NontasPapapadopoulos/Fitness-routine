package com.example.fitness_routine.domain.interactor.cheat


import com.example.fitness_routine.domain.SuspendUseCase
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.CardioRepository
import com.example.fitness_routine.domain.repository.CheatMealRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


open class InitCheatMeal @Inject constructor(
    private val cheatMealRepository: CheatMealRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, InitCheatMeal.Params>(dispatcher) {


    override suspend fun invoke(params: Params) {
        return cheatMealRepository.init(params.date)
    }


    data class Params(val date: Long)
}