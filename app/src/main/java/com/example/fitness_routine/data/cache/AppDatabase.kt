package com.example.fitness_routine.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fitness_routine.data.cache.dao.DailyReportDao
import com.example.fitness_routine.data.cache.dao.ExerciseDao
import com.example.fitness_routine.data.cache.dao.SetDao
import com.example.fitness_routine.data.cache.dao.WorkoutDao
import com.example.fitness_routine.data.entity.BreakDataEntity
import com.example.fitness_routine.data.entity.DailyReportDataEntity
import com.example.fitness_routine.data.entity.ExerciseDataEntity
import com.example.fitness_routine.data.entity.SetDataEntity
import com.example.fitness_routine.data.entity.WorkoutDataEntity
import com.example.fitness_routine.data.util.Converters


@Database(
    entities = [
        DailyReportDataEntity::class,
        WorkoutDataEntity::class,
        SetDataEntity::class,
        ExerciseDataEntity::class,
        BreakDataEntity::class
    ],
    exportSchema = false,
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase(): RoomDatabase() {

    abstract fun getDailyReportDao(): DailyReportDao

    abstract fun getWorkoutDao(): WorkoutDao

    abstract fun getSetDao(): SetDao

    abstract fun getExerciseDao(): ExerciseDao

}