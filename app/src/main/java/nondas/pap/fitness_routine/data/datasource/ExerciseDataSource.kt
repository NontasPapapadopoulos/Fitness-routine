package nondas.pap.fitness_routine.data.datasource

import nondas.pap.fitness_routine.data.entity.ExerciseDataEntity
import kotlinx.coroutines.flow.Flow
import nondas.pap.fitness_routine.data.cache.dao.ExerciseDao
import javax.inject.Inject

interface ExerciseDataSource {

    fun getExercises(): Flow<List<ExerciseDataEntity>>

    suspend fun add(exercise: ExerciseDataEntity)
    suspend fun edit(oldName: String, newName: String)

    suspend fun delete(exercise: ExerciseDataEntity)

}



class ExerciseDataSourceImpl @Inject constructor(
    private val exerciseDao: ExerciseDao
): ExerciseDataSource {
    override fun getExercises(): Flow<List<ExerciseDataEntity>> {
        return exerciseDao.getExercises()
    }

    override suspend fun add(exercise: ExerciseDataEntity) {
        exerciseDao.put(exercise)
    }

    override suspend fun edit(oldName: String, newName: String) {
        exerciseDao.edit(oldName, newName)
    }

    override suspend fun delete(exercise: ExerciseDataEntity) {
        exerciseDao.delete(exercise.name)
    }

}