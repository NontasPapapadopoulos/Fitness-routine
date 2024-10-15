package com.example.fitness_routine.domain.interactor.set


import com.example.fitness_routine.domain.SuspendUseCase
import com.example.fitness_routine.domain.entity.ExerciseDomainEntity
import com.example.fitness_routine.domain.entity.SetDomainEntity
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.SetRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


class CreateNewSet @Inject constructor(
    private val setRepository: SetRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, CreateNewSet.Params>(dispatcher) {


    override suspend fun invoke(params: Params) {
        val emptySet = SetDomainEntity(
            workoutDate = params.workoutDate,
            muscle = params.muscle,
            exercise = params.exercise,
            weight = 0,
            repeats = 0
        )
        return setRepository.addSet(emptySet)
    }


    data class Params(
        val exercise: String,
        val muscle: Muscle,
        val workoutDate: Long
    )
}