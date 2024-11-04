package com.example.fitness_routine.presentation.di

import com.example.fitness_routine.data.cache.dao.DailyReportDao
import com.example.fitness_routine.data.cache.dao.ExerciseDao
import com.example.fitness_routine.data.cache.dao.SetDao
import com.example.fitness_routine.data.cache.dao.SettingsDao
import com.example.fitness_routine.data.cache.dao.WorkoutDao
import com.example.fitness_routine.data.datasource.DailyRoutineDataSource
import com.example.fitness_routine.data.datasource.DailyRoutineDataSourceImpl
import com.example.fitness_routine.data.datasource.ExerciseDataSource
import com.example.fitness_routine.data.datasource.ExerciseDataSourceImpl
import com.example.fitness_routine.data.datasource.SetDataSource
import com.example.fitness_routine.data.datasource.SetDataSourceImpl
import com.example.fitness_routine.data.datasource.SettingsDataSource
import com.example.fitness_routine.data.datasource.SettingsDataSourceImpl
import com.example.fitness_routine.data.datasource.WorkoutDataSource
import com.example.fitness_routine.data.datasource.WorkoutDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideDailyRoutineDataSource(dao: DailyReportDao): DailyRoutineDataSource {
        return DailyRoutineDataSourceImpl(dao)
    }

    @Provides
    @Singleton
    fun provideWorkoutDataSource(dao: WorkoutDao): WorkoutDataSource {
        return WorkoutDataSourceImpl(dao)
    }


    @Provides
    @Singleton
    fun provideSetDataSource(dao: SetDao): SetDataSource {
        return SetDataSourceImpl(dao)
    }


    @Provides
    @Singleton
    fun provideExerciseDataSource(dao: ExerciseDao): ExerciseDataSource {
        return ExerciseDataSourceImpl(dao)
    }


    @Provides
    @Singleton
    fun provideSettingsDataSource(dao: SettingsDao): SettingsDataSource {
        return SettingsDataSourceImpl(dao)
    }
}