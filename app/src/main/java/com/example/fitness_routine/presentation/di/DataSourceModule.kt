//package com.example.fitness_routine.presentation.di
//
//import com.example.fitness_routine.data.cache.dao.CardioDao
//import com.example.fitness_routine.data.cache.dao.CheatMealDao
//import com.example.fitness_routine.data.cache.dao.DailyReportDao
//import com.example.fitness_routine.data.cache.dao.ExerciseDao
//import com.example.fitness_routine.data.cache.dao.NoteDao
//import com.example.fitness_routine.data.cache.dao.SetDao
//import com.example.fitness_routine.data.cache.dao.SettingsDao
//import com.example.fitness_routine.data.cache.dao.WorkoutDao
//import com.example.fitness_routine.data.datasource.AuthDataSource
//import com.example.fitness_routine.data.datasource.AuthDataSourceImpl
//import com.example.fitness_routine.data.datasource.BodyMeasurementDataSource
//import com.example.fitness_routine.data.datasource.BodyMeasurementDataSourceImpl
//import com.example.fitness_routine.data.datasource.CardioDataSource
//import com.example.fitness_routine.data.datasource.NoteDataSource
//import com.example.fitness_routine.data.datasource.CardioDataSourceImpl
//import com.example.fitness_routine.data.datasource.CheatMealDataSource
//import com.example.fitness_routine.data.datasource.CheatMealDataSourceImpl
//import com.example.fitness_routine.data.datasource.DailyRoutineDataSource
//import com.example.fitness_routine.data.datasource.DailyRoutineDataSourceImpl
//import com.example.fitness_routine.data.datasource.ExerciseDataSource
//import com.example.fitness_routine.data.datasource.ExerciseDataSourceImpl
//import com.example.fitness_routine.data.datasource.NoteDataSourceImpl
//import com.example.fitness_routine.data.datasource.SetDataSource
//import com.example.fitness_routine.data.datasource.SetDataSourceImpl
//import com.example.fitness_routine.data.datasource.SettingsDataSource
//import com.example.fitness_routine.data.datasource.SettingsDataSourceImpl
//import com.example.fitness_routine.data.datasource.WorkoutDataSource
//import com.example.fitness_routine.data.datasource.WorkoutDataSourceImpl
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object DataSourceModule {
//
//    @Provides
//    @Singleton
//    fun provideDailyRoutineDataSource(dao: DailyReportDao): DailyRoutineDataSource {
//        return DailyRoutineDataSourceImpl(dao)
//    }
//
//    @Provides
//    @Singleton
//    fun provideWorkoutDataSource(dao: WorkoutDao): WorkoutDataSource {
//        return WorkoutDataSourceImpl(dao)
//    }
//
//
//    @Provides
//    @Singleton
//    fun provideSetDataSource(dao: SetDao): SetDataSource {
//        return SetDataSourceImpl(dao)
//    }
//
//
//    @Provides
//    @Singleton
//    fun provideExerciseDataSource(dao: ExerciseDao): ExerciseDataSource {
//        return ExerciseDataSourceImpl(dao)
//    }
//
//
//    @Provides
//    @Singleton
//    fun provideSettingsDataSource(dao: SettingsDao): SettingsDataSource {
//        return SettingsDataSourceImpl(dao)
//    }
//
//    @Provides
//    @Singleton
//    fun provideCardioDataSource(dao: CardioDao): CardioDataSource {
//        return CardioDataSourceImpl(dao)
//    }
//
//
//    @Provides
//    @Singleton
//    fun provideCheatMealDataSource(dao: CheatMealDao): CheatMealDataSource {
//        return CheatMealDataSourceImpl(dao)
//    }
//
//    @Provides
//    @Singleton
//    fun provideNotesDataSource(dao: NoteDao): NoteDataSource {
//        return NoteDataSourceImpl(dao)
//    }
//
//
//    @Provides
//    @Singleton
//    fun provideBodyMeasurementDataSource(dao: BodyMeasurementDao): BodyMeasurementDataSource {
//        return BodyMeasurementDataSourceImpl(dao)
//    }
//
//
//    @Provides
//    @Singleton
//    fun provideAuthDataSource(): AuthDataSource {
//        return AuthDataSourceImpl()
//    }
//}