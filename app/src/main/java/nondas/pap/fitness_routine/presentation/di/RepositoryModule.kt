package nondas.pap.fitness_routine.presentation.di

import nondas.pap.fitness_routine.data.datasource.BodyMeasurementDataSource
import nondas.pap.fitness_routine.data.datasource.CardioDataSource
import nondas.pap.fitness_routine.data.datasource.CheatMealDataSource
import nondas.pap.fitness_routine.data.datasource.NoteDataSource
import nondas.pap.fitness_routine.data.datasource.DailyRoutineDataSource
import nondas.pap.fitness_routine.data.datasource.ExerciseDataSource
import nondas.pap.fitness_routine.data.datasource.SetDataSource
import nondas.pap.fitness_routine.data.datasource.SettingsDataSource
import nondas.pap.fitness_routine.data.datasource.WorkoutDataSource
import nondas.pap.fitness_routine.data.repository.BodyMeasurementDataRepository
import nondas.pap.fitness_routine.data.repository.CardioDataRepository
import nondas.pap.fitness_routine.data.repository.CheatMealDataRepository
import nondas.pap.fitness_routine.data.repository.DailyRoutineDataRepository
import nondas.pap.fitness_routine.data.repository.ExerciseDataRepository
import nondas.pap.fitness_routine.data.repository.NoteDataRepository
import nondas.pap.fitness_routine.data.repository.SetDataRepository
import nondas.pap.fitness_routine.data.repository.SettingsDataRepository
import nondas.pap.fitness_routine.data.repository.WorkoutDataRepository
import nondas.pap.fitness_routine.domain.repository.BodyMeasurementRepository
import nondas.pap.fitness_routine.domain.repository.CardioRepository
import nondas.pap.fitness_routine.domain.repository.CheatMealRepository
import nondas.pap.fitness_routine.domain.repository.DailyRoutineRepository
import nondas.pap.fitness_routine.domain.repository.ExerciseRepository
import nondas.pap.fitness_routine.domain.repository.NoteRepository
import nondas.pap.fitness_routine.domain.repository.SetRepository
import nondas.pap.fitness_routine.domain.repository.SettingsRepository
import nondas.pap.fitness_routine.domain.repository.WorkoutRepository
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
}