package com.example.fitness_routine.presentation.screen.workout

import androidx.lifecycle.SavedStateHandle
import com.example.fitness_routine.domain.entity.ExerciseDomainEntity
import com.example.fitness_routine.domain.entity.WorkoutWithSetsDomainEntity
import com.example.fitness_routine.domain.interactor.exercise.GetExercises
import com.example.fitness_routine.presentation.BlocViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val getExercises: GetExercises,
    private val savedStateHandle: SavedStateHandle

): BlocViewModel<WorkoutEvent, WorkoutState>() {


    private val exercisesFlow = getExercises.execute(Unit)
        .map { it.getOrThrow() }
        .catch { addError(it) }


    override val _uiState: StateFlow<WorkoutState>
        get() = TODO("Not yet implemented")

}






sealed interface WorkoutEvent {

}



sealed interface WorkoutState {

    object Idle: WorkoutState

    data class Content(
        val workout: WorkoutWithSetsDomainEntity,
        val exercises: List<ExerciseDomainEntity>
    ): WorkoutState
}