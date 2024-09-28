package com.example.fitness_routine.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fitness_routine.data.cache.dao.DailyReportDao
import com.example.fitness_routine.data.entity.DailyReportDataEntity


@Database(
    entities = [
        DailyReportDataEntity::class
    ],
    exportSchema = false,
    version = 1
)
abstract class AppDatabase(): RoomDatabase() {

    abstract fun getDailyReportDao(): DailyReportDao

}