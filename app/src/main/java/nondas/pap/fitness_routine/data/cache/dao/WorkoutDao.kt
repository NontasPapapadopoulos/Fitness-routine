package com.example.fitness_routine.data.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.fitness_routine.data.entity.SetDataEntity
import com.example.fitness_routine.data.entity.WorkoutDataEntity
import com.example.fitness_routine.data.entity.WorkoutWithSetsDataEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface WorkoutDao {

    @Transaction
    @Query("SELECT * FROM Workout WHERE date = :workoutDate")
    fun getWorkoutWithSets(workoutDate: Long): Flow<WorkoutWithSetsDataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun put(workout: WorkoutDataEntity)

    @Update
    suspend fun update(workout: WorkoutDataEntity)

    @Delete
    suspend fun delete(workout: WorkoutDataEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSets(sets: List<SetDataEntity>)

}