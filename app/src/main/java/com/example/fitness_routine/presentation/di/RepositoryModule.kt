package com.example.fitness_routine.presentation.di

import android.content.Context
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
import com.google.firebase.firestore.FirebaseFirestore
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
        firestore: FirebaseFirestore
    ): DailyRoutineRepository {
        return DailyRoutineDataRepository(firestore)
    }


    @Provides
    @Singleton
    fun provideWorkoutRepository(
        firestore: FirebaseFirestore
    ): WorkoutRepository {
        return WorkoutDataRepository(firestore)
    }


    @Provides
    @Singleton
    fun provideSetRepository(
        firestore: FirebaseFirestore
    ): SetRepository {
        return SetDataRepository(firestore)
    }


    @Provides
    @Singleton
    fun provideExerciseRepository(
        firestore: FirebaseFirestore
    ): ExerciseRepository {
        return ExerciseDataRepository(firestore)
    }


    @Provides
    @Singleton
    fun provideSettingsRepository(
        firestore: FirebaseFirestore
    ): SettingsRepository {
        return SettingsDataRepository(firestore)
    }


    @Provides
    @Singleton
    fun provideCardioRepository(
        firestore: FirebaseFirestore
    ): CardioRepository {
        return CardioDataRepository(firestore)
    }

    @Provides
    @Singleton
    fun provideCheatMealRepository(
        firestore: FirebaseFirestore
    ): CheatMealRepository {
        return CheatMealDataRepository(firestore)
    }


    @Provides
    @Singleton
    fun provideNotesRepository(
        firestore: FirebaseFirestore
    ): NoteRepository {
        return NoteDataRepository(firestore)
    }


    @Provides
    @Singleton
    fun provideBodyMeasurementRepository(
        firestore: FirebaseFirestore
    ): BodyMeasurementRepository {
        return BodyMeasurementDataRepository(firestore)
    }


    @Provides
    @Singleton
    fun provideAuthRepository(
        context: Context
    ): AuthRepository {
        return AuthDataRepository(context)
    }
}