package com.example.fitness_routine.data.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.fitness_routine.data.entity.ExerciseDataEntity
import com.example.fitness_routine.domain.entity.enums.Muscle
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercise ")
    fun getExercises(): Flow<List<ExerciseDataEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun put(exercise: ExerciseDataEntity)

    @Delete
    suspend fun delete(exercise: ExerciseDataEntity)

}