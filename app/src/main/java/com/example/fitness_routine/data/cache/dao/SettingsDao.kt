package com.example.fitness_routine.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitness_routine.data.entity.SettingsDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {

    @Query("Select * from Settings")
    fun getSettings(): Flow<SettingsDataEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun put(settings: SettingsDataEntity)


    @Query("UPDATE Settings SET choice = :choice")
    suspend fun changeChoice(choice: String)

}