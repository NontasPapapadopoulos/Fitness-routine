package com.example.fitness_routine.presentation.di

import com.example.fitness_routine.data.datasource.DailyRoutineDataSource
import com.example.fitness_routine.data.datasource.ExerciseDataSource
import com.example.fitness_routine.data.datasource.SetDataSource
import com.example.fitness_routine.data.datasource.WorkoutDataSource
import com.example.fitness_routine.data.repository.DailyRoutineDataRepository
import com.example.fitness_routine.data.repository.ExerciseDataRepository
import com.example.fitness_routine.data.repository.SetDataRepository
import com.example.fitness_routine.data.repository.WorkoutDataRepository
import com.example.fitness_routine.domain.repository.DailyRoutineRepository
import com.example.fitness_routine.domain.repository.ExerciseRepository
import com.example.fitness_routine.domain.repository.SetRepository
import com.example.fitness_routine.domain.repository.WorkoutRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun provideDailyRoutineRepository(
        dailyRoutineDataSource: DailyRoutineDataSource
    ): DailyRoutineRepository {
        return DailyRoutineDataRepository(dailyRoutineDataSource)
    }


    @Provides
    @Singleton
    fun provideWorkoutRepository(
        workoutDataSource: WorkoutDataSource
    ): WorkoutRepository {
        return WorkoutDataRepository(workoutDataSource)
    }


    @Provides
    @Singleton
    fun provideSetRepository(
        setDataSource: SetDataSource
    ): SetRepository {
        return SetDataRepository(setDataSource)
    }


    @Provides
    @Singleton
    fun provideExerciseRepository(
        exerciseDataSource: ExerciseDataSource
    ): ExerciseRepository {
        return ExerciseDataRepository(exerciseDataSource)
    }
}