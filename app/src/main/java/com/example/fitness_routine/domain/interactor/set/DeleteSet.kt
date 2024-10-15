package com.example.fitness_routine.domain.interactor.set


import com.example.fitness_routine.domain.SuspendUseCase
import com.example.fitness_routine.domain.entity.ExerciseDomainEntity
import com.example.fitness_routine.domain.entity.SetDomainEntity
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.SetRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


class DeleteSet @Inject constructor(
    private val setRepository: SetRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, DeleteSet.Params>(dispatcher) {

    override suspend fun invoke(params: Params) {
        return setRepository.deleteSet(params.set)
    }

    data class Params(
        val set: SetDomainEntity
    )
}