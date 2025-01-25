package com.example.fitness_routine.domain.repository

import com.example.fitness_routine.domain.entity.WorkoutDomainEntity
import com.example.fitness_routine.domain.entity.WorkoutWithSetsDomainEntity
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {


    suspend fun addWorkout(workout: WorkoutDomainEntity)

    suspend fun deleteWorkout(workout: WorkoutDomainEntity)

    suspend fun updateWorkout(workout: WorkoutDomainEntity)

}