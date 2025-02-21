package com.example.fitness_routine.domain.interactor.cardio


import com.example.fitness_routine.domain.SuspendUseCase
import com.example.fitness_routine.domain.entity.CardioDomainEntity
import com.example.fitness_routine.domain.entity.ExerciseDomainEntity
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.CardioRepository
import com.example.fitness_routine.domain.repository.ExerciseRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


open class DeleteCardio @Inject constructor(
    private val cardioRepository: CardioRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, DeleteCardio.Params>(dispatcher) {


    override suspend fun invoke(params: Params) {
        return cardioRepository.delete(params.cardio)
    }


    data class Params(val cardio: CardioDomainEntity)
}