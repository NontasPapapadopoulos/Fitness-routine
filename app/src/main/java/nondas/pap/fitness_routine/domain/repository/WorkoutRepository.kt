package nondas.pap.fitness_routine.domain.repository

import nondas.pap.fitness_routine.domain.entity.WorkoutDomainEntity
import nondas.pap.fitness_routine.domain.entity.WorkoutWithSetsDomainEntity
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {

    fun getWorkoutWithSets(date: Long): Flow<WorkoutWithSetsDomainEntity>

    suspend fun addWorkout(workout: WorkoutDomainEntity)

    suspend fun deleteWorkout(workout: WorkoutDomainEntity)

    suspend fun updateWorkout(workout: WorkoutDomainEntity)

}