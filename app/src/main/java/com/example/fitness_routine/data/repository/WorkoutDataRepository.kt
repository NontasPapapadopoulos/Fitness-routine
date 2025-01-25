package com.example.fitness_routine.data.repository

import com.example.fitness_routine.data.mapper.toData
import com.example.fitness_routine.data.mapper.toDomain
import com.example.fitness_routine.domain.entity.WorkoutDomainEntity
import com.example.fitness_routine.domain.repository.WorkoutRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WorkoutDataRepository @Inject constructor(
    private val firestore: FirebaseFirestore
): WorkoutRepository {


    override suspend fun addWorkout(workout: WorkoutDomainEntity) {
//        workoutDataSource.addWorkout(workout.toData())
    }

    override suspend fun deleteWorkout(workout: WorkoutDomainEntity) {
//        workoutDataSource.deleteWorkout(workout.toData())
    }

    override suspend fun updateWorkout(workout: WorkoutDomainEntity) {
//        workoutDataSource.updateWorkout(workout.toData())
    }
}