package com.example.fitness_routine.data.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitness_routine.data.entity.NoteDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note WHERE date = :date")
    fun getNotesFlow(date: Long): Flow<List<NoteDataEntity>>

    @Query("SELECT * FROM note")
    fun getNotesFlow(): Flow<List<NoteDataEntity>>

    @Query("SELECT COUNT(*) FROM note WHERE date = :date")
    fun getNumberOfNotes(date: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun put(note: NoteDataEntity)

    @Delete
    suspend fun delete(note: NoteDataEntity)
}