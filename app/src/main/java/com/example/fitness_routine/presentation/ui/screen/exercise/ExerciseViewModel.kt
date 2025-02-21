package com.example.fitness_routine.presentation.ui.screen.exercise

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.fitness_routine.domain.entity.ExerciseDomainEntity
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.domain.interactor.exercise.AddExercise
import com.example.fitness_routine.domain.interactor.exercise.DeleteExercise
import com.example.fitness_routine.domain.interactor.exercise.EditExercise
import com.example.fitness_routine.domain.interactor.exercise.GetExercises
import com.example.fitness_routine.presentation.BlocViewModel
import com.example.fitness_routine.presentation.navigation.NavigationArgument
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
open class ExerciseViewModel @Inject constructor(
    private val getExercises: GetExercises,
    private val addExercise: AddExercise,
    private val deleteExercise: DeleteExercise,
    private val editExercise: EditExercise,
    private val savedStateHandle: SavedStateHandle,
): BlocViewModel<ExerciseEvent, ExerciseState>() {

    private val muscle get() = savedStateHandle.get<String>(NavigationArgument.Muscle.param)

    private val newExerciseFlow = MutableSharedFlow<String>()
    private val newNameExerciseFlow = MutableSharedFlow<String>()
    private val selectedExerciseFlow = MutableSharedFlow<ExerciseDomainEntity?>()

    private val exercisesFlow = getExercises.execute(Unit)
        .map { it.getOrThrow() }
        .catch { addError(it) }

    override val _uiState: StateFlow<ExerciseState> = combine(
        newExerciseFlow.onStart { emit("") },
        newNameExerciseFlow.onStart { emit("") },
        exercisesFlow.onStart { emit(listOf()) },
        selectedExerciseFlow.onStart { emit(null) }
    ) { newExercise, newName, exercises, selectedExercise ->

        ExerciseState.Content(
            newExercise = newExercise,
            newName = newName,
            exercises = exercises,
            preSelectedMuscle = muscle?.takeIf { it != "null" }?.let { Muscle.valueOf(it) },
            selectedExercise = selectedExercise
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
                    onSuccess = { newExerciseFlow.emit("") },
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

        on(ExerciseEvent.TextChanged::class) {
            newExerciseFlow.emit(it.text)
        }

        on(ExerciseEvent.SelectExercise::class) {
            selectedExerciseFlow.emit(it.exercise)
        }

        on(ExerciseEvent.UpdateExercise::class) {
            onState<ExerciseState.Content> { state ->
                editExercise.execute(EditExercise.Params(state.selectedExercise!!.name, state.newName))
                    .fold(
                        onSuccess = {
                            selectedExerciseFlow.emit(null)
                            newNameExerciseFlow.emit("")
                                    },
                        onFailure = { addError(it) }
                    )
            }
        }

        on(ExerciseEvent.NewExerciseNameTextChanged::class) {
            newNameExerciseFlow.emit(it.text)
        }

        on(ExerciseEvent.DismissDialog::class) {
            selectedExerciseFlow.emit(null)
        }

    }
}


sealed interface ExerciseEvent {
    data class Delete(val exercise: ExerciseDomainEntity): ExerciseEvent
    data class Add(val muscle: Muscle): ExerciseEvent
    data class TextChanged(val text: String): ExerciseEvent
    data class NewExerciseNameTextChanged(val text: String): ExerciseEvent
    data class SelectExercise(val exercise: ExerciseDomainEntity): ExerciseEvent
    object UpdateExercise: ExerciseEvent
    object DismissDialog: ExerciseEvent
}


sealed interface ExerciseState {
    object Idle: ExerciseState

    data class Content(
        val exercises: List<ExerciseDomainEntity>,
        val newExercise: String,
        val newName: String,
        val preSelectedMuscle: Muscle?,
        val selectedExercise: ExerciseDomainEntity?
    ): ExerciseState
}