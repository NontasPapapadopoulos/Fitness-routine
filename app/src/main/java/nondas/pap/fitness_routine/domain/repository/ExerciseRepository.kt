package nondas.pap.fitness_routine.domain.repository

import nondas.pap.fitness_routine.data.entity.ExerciseDataEntity
import nondas.pap.fitness_routine.domain.entity.ExerciseDomainEntity
import nondas.pap.fitness_routine.domain.entity.enums.Muscle
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {

    fun getExercises(): Flow<List<ExerciseDomainEntity>>

    suspend fun add(exercise: ExerciseDomainEntity)
    suspend fun edit(oldName: String, newName: String)

    suspend fun delete(exercise: ExerciseDomainEntity)

}