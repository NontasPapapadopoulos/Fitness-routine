package com.example.fitness_routine.domain.interactor.exercise

import com.example.fitness_routine.domain.FlowUseCase
import com.example.fitness_routine.domain.entity.CheatMealDomainEntity
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.ExerciseDomainEntity
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.DailyRoutineRepository
import com.example.fitness_routine.domain.repository.ExerciseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetExercises @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<List<ExerciseDomainEntity>, Unit>(dispatcher) {


    override fun invoke(params: Unit): Flow<List<ExerciseDomainEntity>> {
        return exerciseRepository.getExercises()

    }
}