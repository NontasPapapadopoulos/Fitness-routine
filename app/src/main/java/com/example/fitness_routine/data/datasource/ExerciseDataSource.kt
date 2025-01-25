package com.example.fitness_routine.data.datasource

import com.example.fitness_routine.data.entity.ExerciseDataEntity
import com.example.fitness_routine.domain.entity.enums.Muscle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ExerciseDataSource {

    fun getExercises(): Flow<List<ExerciseDataEntity>>

    suspend fun add(exercise: ExerciseDataEntity)
    suspend fun edit(oldName: String, newName: String)

    suspend fun delete(exercise: ExerciseDataEntity)

}


//
//class ExerciseDataSourceImpl @Inject constructor(
//    private val exerciseDao: ExerciseDao
//): ExerciseDataSource {
//    override fun getExercises(): Flow<List<ExerciseDataEntity>> {
//        return exerciseDao.getExercises()
//    }
//
//    override suspend fun add(exercise: ExerciseDataEntity) {
//        exerciseDao.put(exercise)
//    }
//
//    override suspend fun edit(oldName: String, newName: String) {
//        exerciseDao.edit(oldName, newName)
//    }
//
//    override suspend fun delete(exercise: ExerciseDataEntity) {
//        exerciseDao.delete(exercise.name)
//    }
//
//}