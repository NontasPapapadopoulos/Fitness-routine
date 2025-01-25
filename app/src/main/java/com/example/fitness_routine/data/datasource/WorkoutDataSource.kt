package com.example.fitness_routine.data.datasource

import com.example.fitness_routine.data.entity.WorkoutDataEntity
import com.example.fitness_routine.domain.entity.WorkoutDomainEntity
import com.example.fitness_routine.domain.entity.WorkoutWithSetsDomainEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface WorkoutDataSource {


    suspend fun addWorkout(workout: WorkoutDataEntity)

    suspend fun deleteWorkout(workout: WorkoutDataEntity)

    suspend fun updateWorkout(workout: WorkoutDataEntity)
}


//
//class WorkoutDataSourceImpl @Inject constructor(
//    private val workoutDao: WorkoutDao
//): WorkoutDataSource {
//    override fun getWorkoutWithSets(date: Long): Flow<WorkoutWithSetsDataEntity> {
//        return workoutDao.getWorkoutWithSets(date)
//    }
//
//    override suspend fun addWorkout(workout: WorkoutDataEntity) {
//        workoutDao.put(workout)
//    }
//
//    override suspend fun deleteWorkout(workout: WorkoutDataEntity) {
//        workoutDao.delete(workout)
//    }
//
//    override suspend fun updateWorkout(workout: WorkoutDataEntity) {
//        workoutDao.update(workout)
//    }
//
//}