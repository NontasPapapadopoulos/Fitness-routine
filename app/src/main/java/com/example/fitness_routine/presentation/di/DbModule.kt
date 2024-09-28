package com.example.fitness_routine.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.fitness_routine.data.cache.AppDatabase
import com.example.fitness_routine.data.cache.dao.DailyReportDao
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

}