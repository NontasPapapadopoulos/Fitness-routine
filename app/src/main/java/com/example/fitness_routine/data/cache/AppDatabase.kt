package com.example.fitness_routine.data.cache

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fitness_routine.data.cache.dao.BodyMeasurementDao
import com.example.fitness_routine.data.cache.dao.CardioDao
import com.example.fitness_routine.data.cache.dao.CheatMealDao
import com.example.fitness_routine.data.cache.dao.DailyReportDao
import com.example.fitness_routine.data.cache.dao.ExerciseDao
import com.example.fitness_routine.data.cache.dao.NoteDao
import com.example.fitness_routine.data.cache.dao.SetDao
import com.example.fitness_routine.data.cache.dao.SettingsDao
import com.example.fitness_routine.data.cache.dao.WorkoutDao
import com.example.fitness_routine.data.entity.BodyMeasurementDataEntity
import com.example.fitness_routine.data.entity.BreakDataEntity
import com.example.fitness_routine.data.entity.CardioDataEntity
import com.example.fitness_routine.data.entity.CheatMealDataEntity
import com.example.fitness_routine.data.entity.DailyReportDataEntity
import com.example.fitness_routine.data.entity.ExerciseDataEntity
import com.example.fitness_routine.data.entity.NoteDataEntity
import com.example.fitness_routine.data.entity.SetDataEntity
import com.example.fitness_routine.data.entity.SettingsDataEntity
import com.example.fitness_routine.data.entity.WorkoutDataEntity
import com.example.fitness_routine.data.util.Converters


@Database(
    entities = [
        DailyReportDataEntity::class,
        WorkoutDataEntity::class,
        SetDataEntity::class,
        ExerciseDataEntity::class,
        BreakDataEntity::class,
        SettingsDataEntity::class,
        CardioDataEntity::class,
        NoteDataEntity::class,
        CheatMealDataEntity::class,
        BodyMeasurementDataEntity::class
    ],
//    autoMigrations = [
//        AutoMigration(from = 4, to = 5, )//spec = Migrations.Migrate4to5::class)
//                     ],
    exportSchema = false,
    version = 5
)
@TypeConverters(Converters::class)
abstract class AppDatabase(): RoomDatabase() {

    abstract fun getDailyReportDao(): DailyReportDao

    abstract fun getWorkoutDao(): WorkoutDao

    abstract fun getSetDao(): SetDao

    abstract fun getExerciseDao(): ExerciseDao

    abstract fun getSettingsDao(): SettingsDao

    abstract fun getCardioDao(): CardioDao

    abstract fun getCheatMealDao(): CheatMealDao

    abstract fun getNoteDao(): NoteDao

    abstract fun getBodyMeasurementDao(): BodyMeasurementDao
}