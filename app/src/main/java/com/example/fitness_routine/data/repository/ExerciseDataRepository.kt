package com.example.fitness_routine.data.repository

import androidx.datastore.dataStore
import com.example.fitness_routine.data.datasource.ExerciseDataSource
import com.example.fitness_routine.data.mapper.toData
import com.example.fitness_routine.data.mapper.toDomain
import com.example.fitness_routine.domain.entity.ExerciseDomainEntity
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExerciseDataRepository @Inject constructor(
    private val exerciseDataSource: ExerciseDataSource
): ExerciseRepository {
    override fun getExercises(): Flow<List<ExerciseDomainEntity>> {
        return exerciseDataSource.getExercises()
            .map { muscles ->
                muscles.map { it.toDomain() }
            }
    }

    override suspend fun add(exercise: ExerciseDomainEntity) {
        exerciseDataSource.add(exercise.toData())
    }

    override suspend fun edit(oldName: String, newName: String) {
        exerciseDataSource.edit(oldName, newName)
    }

    override suspend fun delete(exercise: ExerciseDomainEntity) {
        exerciseDataSource.delete(exercise.toData())
    }
}