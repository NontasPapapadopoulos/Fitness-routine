package com.example.fitness_routine.data.cache.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.fitness_routine.data.entity.SettingsDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {

    @Query("Select * from Settings")
    fun getSettings(): Flow<SettingsDataEntity>

}