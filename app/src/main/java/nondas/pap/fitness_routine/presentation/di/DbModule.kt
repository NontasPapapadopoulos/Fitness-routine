package nondas.pap.fitness_routine.presentation.di

import android.app.Application
import androidx.room.Room
import nondas.pap.fitness_routine.data.cache.AppDatabase
import nondas.pap.fitness_routine.data.cache.dao.BodyMeasurementDao
import nondas.pap.fitness_routine.data.cache.dao.CardioDao
import nondas.pap.fitness_routine.data.cache.dao.CheatMealDao
import nondas.pap.fitness_routine.data.cache.dao.DailyReportDao
import nondas.pap.fitness_routine.data.cache.dao.ExerciseDao
import nondas.pap.fitness_routine.data.cache.dao.NoteDao
import nondas.pap.fitness_routine.data.cache.dao.SetDao
import nondas.pap.fitness_routine.data.cache.dao.SettingsDao
import nondas.pap.fitness_routine.data.cache.dao.WorkoutDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DbModule {


    @Provides
    @Singleton
    fun providesDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "fitness-routine-db")
            .fallbackToDestructiveMigration().build()
    }


    @Provides
    @Singleton
    fun provideDailyReportDao(database: AppDatabase): DailyReportDao {
        return database.getDailyReportDao()
    }

    @Provides
    @Singleton
    fun provideWorkoutDao(database: AppDatabase): WorkoutDao {
        return database.getWorkoutDao()
    }

    @Provides
    @Singleton
    fun provideSetDao(database: AppDatabase): SetDao {
        return database.getSetDao()
    }

    @Provides
    @Singleton
    fun provideExerciseDao(database: AppDatabase): ExerciseDao {
        return database.getExerciseDao()
    }


    @Provides
    @Singleton
    fun provideSettingsDao(database: AppDatabase): SettingsDao {
        return database.getSettingsDao()
    }

    @Provides
    @Singleton
    fun provideCardioDao(database: AppDatabase): CardioDao {
        return database.getCardioDao()
    }


    @Provides
    @Singleton
    fun provideNoteDao(database: AppDatabase): NoteDao {
        return database.getNoteDao()
    }

    @Provides
    @Singleton
    fun provideCheatMealDao(database: AppDatabase): CheatMealDao {
        return database.getCheatMealDao()
    }

    @Provides
    @Singleton
    fun provideBodyMeasurementDao(database: AppDatabase): BodyMeasurementDao {
        return database.getBodyMeasurementDao()
    }

}