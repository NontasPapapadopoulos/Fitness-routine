package com.example.fitness_routine.data.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.fitness_routine.data.entity.DailyReportDataEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date


@Dao
interface DailyReportDao {

    @Query("SELECT * FROM DailyReport")
    fun getReports(): Flow<DailyReportDataEntity>

    @Query("SELECT * FROM DailyReport WHERE date = :date")
    fun getReport(date: Date): DailyReportDataEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun put(dailyReportDataEntity: DailyReportDataEntity)

    @Update
    suspend fun update(report: DailyReportDataEntity, date: Date)

    @Delete
    suspend fun delete(dailyReport: DailyReportDataEntity)

}