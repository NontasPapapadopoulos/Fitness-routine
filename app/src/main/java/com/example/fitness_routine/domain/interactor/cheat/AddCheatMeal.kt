package com.example.fitness_routine.domain.interactor.cheat


import com.example.fitness_routine.domain.SuspendUseCase
import com.example.fitness_routine.domain.entity.CheatMealDomainEntity
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.CheatMealRepository
import kotlinx.coroutines.CoroutineDispatcher
import java.util.Date
import java.util.UUID
import javax.inject.Inject


class AddCheatMeal @Inject constructor(
    private val cheatMealRepository: CheatMealRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, AddCheatMeal.Params>(dispatcher) {

    override suspend fun invoke(params: Params) {

        val cheatMeal = CheatMealDomainEntity(
            id = UUID.randomUUID().toString(),
            text = params.meal,
            date = params.date
        )

        return cheatMealRepository.put(cheatMeal)
    }


    data class Params(
        val date: Date,
        val meal: String,
    )
}