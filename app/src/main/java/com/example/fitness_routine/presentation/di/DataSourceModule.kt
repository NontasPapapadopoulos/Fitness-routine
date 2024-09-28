package com.example.fitness_routine.presentation.di

import com.example.fitness_routine.data.cache.dao.DailyReportDao
import com.example.fitness_routine.data.datasource.DailyRoutineDataSource
import com.example.fitness_routine.data.datasource.DailyRoutineDataSourceImpl
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
}