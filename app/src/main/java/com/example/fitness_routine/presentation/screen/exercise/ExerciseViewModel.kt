package com.example.fitness_routine.presentation.screen.exercise

import androidx.lifecycle.viewModelScope
import com.example.fitness_routine.domain.entity.ExerciseDomainEntity
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.domain.interactor.exercise.AddExercise
import com.example.fitness_routine.domain.interactor.exercise.DeleteExercise
import com.example.fitness_routine.domain.interactor.exercise.GetExercises
import com.example.fitness_routine.presentation.BlocViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val getExercises: GetExercises,
    private val addExercise: AddExercise,
    private val deleteExercise: DeleteExercise
): BlocViewModel<ExerciseEvent, ExerciseState>() {

    private val newExerciseFlow = MutableSharedFlow<String>()

    private val exercisesFlow = getExercises.execute(Unit)
        .map { it.getOrThrow() }
        .catch { addError(it) }

    override val _uiState: StateFlow<ExerciseState> = combine(
        newExerciseFlow.onStart { emit("") },
        exercisesFlow.onStart { emit(listOf()) }
    ) { newExercise, exercises ->

        ExerciseState.Content(
            newExercise = newExercise,
            exercises = exercises
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ExerciseState.Idle
    )


    init {

        on(ExerciseEvent.Add::class) {
            onState<ExerciseState.Content> { state ->
                val exercise = ExerciseDomainEntity(
                    name = state.newExercise,
                    muscle = it.muscle,
//                    index = it
                )

                addExercise.execute(AddExercise.Params(exercise)).fold(
                    onSuccess = {},
                    onFailure = { addError(it) }
                )
            }

        }

        on(ExerciseEvent.Delete::class) {
            deleteExercise.execute(DeleteExercise.Params(it.exercise)).fold(
                onSuccess = {},
                onFailure = { addError(it) }
            )
        }



    }
}


sealed interface ExerciseEvent {
    data class Delete(val exercise: ExerciseDomainEntity): ExerciseEvent
    data class Add(val muscle: Muscle): ExerciseEvent
    data class TextChanged(val text: String): ExerciseEvent
}


sealed interface ExerciseState {
    object Idle: ExerciseState

    data class Content(
        val exercises: List<ExerciseDomainEntity>,
        val newExercise: String
    ): ExerciseState
}