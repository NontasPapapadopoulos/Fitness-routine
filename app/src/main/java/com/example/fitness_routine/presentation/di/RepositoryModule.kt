package com.example.fitness_routine.presentation.di

import com.example.fitness_routine.data.datasource.DailyRoutineDataSource
import com.example.fitness_routine.data.repository.DailyRoutineDataRepository
import com.example.fitness_routine.domain.repository.DailyRoutineRepository
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

}