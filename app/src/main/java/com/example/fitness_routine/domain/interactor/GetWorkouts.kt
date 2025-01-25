package com.example.fitness_routine.domain.interactor

import com.example.fitness_routine.domain.FlowUseCase

import com.example.fitness_routine.domain.entity.WorkoutDomainEntity
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.DailyRoutineRepository
import com.example.fitness_routine.domain.toMuscles
import com.example.fitness_routine.domain.toTimeStamp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetWorkouts @Inject constructor(
    private val dailyRoutineRepository: DailyRoutineRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<List<WorkoutDomainEntity>, Unit>(dispatcher) {


    override fun invoke(params: Unit): Flow<List<WorkoutDomainEntity>> {
        return dailyRoutineRepository.getDailyReports()
                .map { report ->
                    report.filter { it.performedWorkout }
                        .map { WorkoutDomainEntity(it.date, it.musclesTrained.toMuscles(), listOf()) }
                }

    }
}