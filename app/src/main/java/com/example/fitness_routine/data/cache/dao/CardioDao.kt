package com.example.fitness_routine.data.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitness_routine.data.entity.CardioDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardioDao {

    @Query("SELECT * FROM cardio WHERE date = :date")
    fun getCardiosFlow(date: Long): Flow<List<CardioDataEntity>>

    @Query("SELECT COUNT(*) FROM cardio WHERE date = :date")
    fun getNumberOfCardios(date: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun put(cardio: CardioDataEntity)

//    @Query("DELETE FROM cardio WHERE id =:id")
//    suspend fun delete(id: String)


    @Delete
    suspend fun delete(cardio: CardioDataEntity)
}