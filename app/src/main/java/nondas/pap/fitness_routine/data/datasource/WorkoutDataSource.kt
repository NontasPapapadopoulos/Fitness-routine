package nondas.pap.fitness_routine.data.datasource

import nondas.pap.fitness_routine.data.entity.WorkoutDataEntity
import nondas.pap.fitness_routine.data.entity.WorkoutWithSetsDataEntity
import kotlinx.coroutines.flow.Flow
import nondas.pap.fitness_routine.data.cache.dao.WorkoutDao
import javax.inject.Inject

interface WorkoutDataSource {

    fun getWorkoutWithSets(date: Long): Flow<WorkoutWithSetsDataEntity>

    suspend fun addWorkout(workout: WorkoutDataEntity)

    suspend fun deleteWorkout(workout: WorkoutDataEntity)

    suspend fun updateWorkout(workout: WorkoutDataEntity)
}



class WorkoutDataSourceImpl @Inject constructor(
    private val workoutDao: WorkoutDao
): WorkoutDataSource {
    override fun getWorkoutWithSets(date: Long): Flow<WorkoutWithSetsDataEntity> {
        return workoutDao.getWorkoutWithSets(date)
    }

    override suspend fun addWorkout(workout: WorkoutDataEntity) {
        workoutDao.put(workout)
    }

    override suspend fun deleteWorkout(workout: WorkoutDataEntity) {
        workoutDao.delete(workout)
    }

    override suspend fun updateWorkout(workout: WorkoutDataEntity) {
        workoutDao.update(workout)
    }

}