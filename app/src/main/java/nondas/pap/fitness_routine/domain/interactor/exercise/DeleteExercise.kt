package com.example.fitness_routine.domain.interactor.exercise


import com.example.fitness_routine.domain.SuspendUseCase
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.ExerciseDomainEntity
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.DailyRoutineRepository
import com.example.fitness_routine.domain.repository.ExerciseRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


class DeleteExercise @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, DeleteExercise.Params>(dispatcher) {


    override suspend fun invoke(params: Params) {
        return exerciseRepository.delete(params.exercise)
    }


    data class Params(val exercise: ExerciseDomainEntity)
}