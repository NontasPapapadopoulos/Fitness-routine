package com.example.fitness_routine.presentation.di

import com.example.fitness_routine.data.datasource.AuthDataSource
import com.example.fitness_routine.data.datasource.BodyMeasurementDataSource
import com.example.fitness_routine.data.datasource.CardioDataSource
import com.example.fitness_routine.data.datasource.CheatMealDataSource
import com.example.fitness_routine.data.datasource.NoteDataSource
import com.example.fitness_routine.data.datasource.DailyRoutineDataSource
import com.example.fitness_routine.data.datasource.ExerciseDataSource
import com.example.fitness_routine.data.datasource.SetDataSource
import com.example.fitness_routine.data.datasource.SettingsDataSource
import com.example.fitness_routine.data.datasource.WorkoutDataSource
import com.example.fitness_routine.data.repository.AuthDataRepository
import com.example.fitness_routine.data.repository.BodyMeasurementDataRepository
import com.example.fitness_routine.data.repository.CardioDataRepository
import com.example.fitness_routine.data.repository.CheatMealDataRepository
import com.example.fitness_routine.data.repository.DailyRoutineDataRepository
import com.example.fitness_routine.data.repository.ExerciseDataRepository
import com.example.fitness_routine.data.repository.NoteDataRepository
import com.example.fitness_routine.data.repository.SetDataRepository
import com.example.fitness_routine.data.repository.SettingsDataRepository
import com.example.fitness_routine.data.repository.WorkoutDataRepository
import com.example.fitness_routine.domain.repository.AuthRepository
import com.example.fitness_routine.domain.repository.BodyMeasurementRepository
import com.example.fitness_routine.domain.repository.CardioRepository
import com.example.fitness_routine.domain.repository.CheatMealRepository
import com.example.fitness_routine.domain.repository.DailyRoutineRepository
import com.example.fitness_routine.domain.repository.ExerciseRepository
import com.example.fitness_routine.domain.repository.NoteRepository
import com.example.fitness_routine.domain.repository.SetRepository
import com.example.fitness_routine.domain.repository.SettingsRepository
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


    @Provides
    @Singleton
    fun provideSettingsRepository(
        settingsDataSource: SettingsDataSource
    ): SettingsRepository {
        return SettingsDataRepository(settingsDataSource)
    }


    @Provides
    @Singleton
    fun provideCardioRepository(
        cardioDataSource: CardioDataSource
    ): CardioRepository {
        return CardioDataRepository(cardioDataSource)
    }

    @Provides
    @Singleton
    fun provideCheatMealRepository(
        cheatMealDataSource: CheatMealDataSource
    ): CheatMealRepository {
        return CheatMealDataRepository(cheatMealDataSource)
    }


    @Provides
    @Singleton
    fun provideNotesRepository(
        noteDataSource: NoteDataSource
    ): NoteRepository {
        return NoteDataRepository(noteDataSource)
    }


    @Provides
    @Singleton
    fun provideBodyMeasurementRepository(
        bodyMeasurementDataSource: BodyMeasurementDataSource
    ): BodyMeasurementRepository {
        return BodyMeasurementDataRepository(bodyMeasurementDataSource)
    }


    @Provides
    @Singleton
    fun provideAuthRepository(
        authDataSource: AuthDataSource
    ): AuthRepository {
        return AuthDataRepository(authDataSource)
    }
}