package nondas.pap.fitness_routine.presentation.di

import nondas.pap.fitness_routine.data.cache.dao.BodyMeasurementDao
import nondas.pap.fitness_routine.data.cache.dao.CardioDao
import nondas.pap.fitness_routine.data.cache.dao.CheatMealDao
import nondas.pap.fitness_routine.data.cache.dao.DailyReportDao
import nondas.pap.fitness_routine.data.cache.dao.ExerciseDao
import nondas.pap.fitness_routine.data.cache.dao.NoteDao
import nondas.pap.fitness_routine.data.cache.dao.SetDao
import nondas.pap.fitness_routine.data.cache.dao.SettingsDao
import nondas.pap.fitness_routine.data.cache.dao.WorkoutDao
import nondas.pap.fitness_routine.data.datasource.BodyMeasurementDataSource
import nondas.pap.fitness_routine.data.datasource.BodyMeasurementDataSourceImpl
import nondas.pap.fitness_routine.data.datasource.CardioDataSource
import nondas.pap.fitness_routine.data.datasource.NoteDataSource
import nondas.pap.fitness_routine.data.datasource.CardioDataSourceImpl
import nondas.pap.fitness_routine.data.datasource.CheatMealDataSource
import nondas.pap.fitness_routine.data.datasource.CheatMealDataSourceImpl
import nondas.pap.fitness_routine.data.datasource.DailyRoutineDataSource
import nondas.pap.fitness_routine.data.datasource.DailyRoutineDataSourceImpl
import nondas.pap.fitness_routine.data.datasource.ExerciseDataSource
import nondas.pap.fitness_routine.data.datasource.ExerciseDataSourceImpl
import nondas.pap.fitness_routine.data.datasource.NoteDataSourceImpl
import nondas.pap.fitness_routine.data.datasource.SetDataSource
import nondas.pap.fitness_routine.data.datasource.SetDataSourceImpl
import nondas.pap.fitness_routine.data.datasource.SettingsDataSource
import nondas.pap.fitness_routine.data.datasource.SettingsDataSourceImpl
import nondas.pap.fitness_routine.data.datasource.WorkoutDataSource
import nondas.pap.fitness_routine.data.datasource.WorkoutDataSourceImpl
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

    @Provides
    @Singleton
    fun provideCardioDataSource(dao: CardioDao): CardioDataSource {
        return CardioDataSourceImpl(dao)
    }


    @Provides
    @Singleton
    fun provideCheatMealDataSource(dao: CheatMealDao): CheatMealDataSource {
        return CheatMealDataSourceImpl(dao)
    }

    @Provides
    @Singleton
    fun provideNotesDataSource(dao: NoteDao): NoteDataSource {
        return NoteDataSourceImpl(dao)
    }


    @Provides
    @Singleton
    fun provideBodyMeasurementDataSource(dao: BodyMeasurementDao): BodyMeasurementDataSource {
        return BodyMeasurementDataSourceImpl(dao)
    }
}