package nondas.pap.fitness_routine.data.repository

import nondas.pap.fitness_routine.data.datasource.WorkoutDataSource
import nondas.pap.fitness_routine.data.mapper.toData
import nondas.pap.fitness_routine.data.mapper.toDomain
import nondas.pap.fitness_routine.domain.entity.WorkoutDomainEntity
import nondas.pap.fitness_routine.domain.entity.WorkoutWithSetsDomainEntity
import nondas.pap.fitness_routine.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WorkoutDataRepository @Inject constructor(
    private val workoutDataSource: WorkoutDataSource
): WorkoutRepository {
    override fun getWorkoutWithSets(date: Long): Flow<WorkoutWithSetsDomainEntity> {
        return workoutDataSource.getWorkoutWithSets(date).map { it.toDomain() }
    }

    override suspend fun addWorkout(workout: WorkoutDomainEntity) {
        workoutDataSource.addWorkout(workout.toData())
    }

    override suspend fun deleteWorkout(workout: WorkoutDomainEntity) {
        workoutDataSource.deleteWorkout(workout.toData())
    }

    override suspend fun updateWorkout(workout: WorkoutDomainEntity) {
        workoutDataSource.updateWorkout(workout.toData())
    }
}