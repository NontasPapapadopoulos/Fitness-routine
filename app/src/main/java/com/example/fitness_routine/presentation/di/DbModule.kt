package com.example.fitness_routine.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.fitness_routine.data.cache.AppDatabase
import com.example.fitness_routine.data.cache.dao.DailyReportDao
import com.example.fitness_routine.data.cache.dao.ExerciseDao
import com.example.fitness_routine.data.cache.dao.SetDao
import com.example.fitness_routine.data.cache.dao.SettingsDao
import com.example.fitness_routine.data.cache.dao.WorkoutDao
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
}