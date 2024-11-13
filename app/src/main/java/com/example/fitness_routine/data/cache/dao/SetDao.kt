package com.example.fitness_routine.data.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.fitness_routine.data.entity.SetDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SetDao {

    @Query("SELECT * FROM `set` WHERE workoutDate = :date")
    fun getSets(date: Long): Flow<List<SetDataEntity>>

    @Insert
    suspend fun put(set: SetDataEntity)

    @Update
    suspend fun update(set: SetDataEntity)

    @Delete
    suspend fun delete(set: SetDataEntity)
}