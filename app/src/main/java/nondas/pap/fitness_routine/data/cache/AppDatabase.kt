package nondas.pap.fitness_routine.data.cache

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import nondas.pap.fitness_routine.data.entity.BodyMeasurementDataEntity
import nondas.pap.fitness_routine.data.entity.CardioDataEntity
import nondas.pap.fitness_routine.data.entity.CheatMealDataEntity
import nondas.pap.fitness_routine.data.entity.DailyReportDataEntity
import nondas.pap.fitness_routine.data.entity.ExerciseDataEntity
import nondas.pap.fitness_routine.data.entity.NoteDataEntity
import nondas.pap.fitness_routine.data.entity.SetDataEntity
import nondas.pap.fitness_routine.data.entity.SettingsDataEntity
import nondas.pap.fitness_routine.data.entity.WorkoutDataEntity
import nondas.pap.fitness_routine.data.util.Converters
import nondas.pap.fitness_routine.data.cache.dao.BodyMeasurementDao
import nondas.pap.fitness_routine.data.cache.dao.CardioDao
import nondas.pap.fitness_routine.data.cache.dao.CheatMealDao
import nondas.pap.fitness_routine.data.cache.dao.DailyReportDao
import nondas.pap.fitness_routine.data.cache.dao.ExerciseDao
import nondas.pap.fitness_routine.data.cache.dao.NoteDao
import nondas.pap.fitness_routine.data.cache.dao.SetDao
import nondas.pap.fitness_routine.data.cache.dao.SettingsDao
import nondas.pap.fitness_routine.data.cache.dao.WorkoutDao


@Database(
    entities = [
        DailyReportDataEntity::class,
        WorkoutDataEntity::class,
        SetDataEntity::class,
        ExerciseDataEntity::class,
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
    version = 6
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