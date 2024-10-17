package com.example.fitness_routine.presentation.screen.workout

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.fitness_routine.domain.entity.ExerciseDomainEntity
import com.example.fitness_routine.domain.entity.SetDomainEntity
import com.example.fitness_routine.domain.entity.WorkoutWithSetsDomainEntity
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.domain.interactor.exercise.GetExercises
import com.example.fitness_routine.domain.interactor.report.GetDailyReport
import com.example.fitness_routine.domain.interactor.set.CreateNewSet
import com.example.fitness_routine.domain.interactor.set.DeleteSet
import com.example.fitness_routine.domain.interactor.set.GetSets
import com.example.fitness_routine.domain.toMuscles
import com.example.fitness_routine.presentation.BlocViewModel
import com.example.fitness_routine.presentation.navigation.NavigationArgument
import com.example.fitness_routine.presentation.toDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val getExercises: GetExercises,
    private val getSets: GetSets,
    private val getDailyReport: GetDailyReport,
    private val createNewSet: CreateNewSet,
    private val deleteSet: DeleteSet,
    private val savedStateHandle: SavedStateHandle
): BlocViewModel<WorkoutEvent, WorkoutState>() {

    private val date get() = savedStateHandle.get<Long>(NavigationArgument.Date.param)!!

    private val exercisesFlow = getExercises.execute(Unit)
        .map { it.getOrThrow() }
        .catch { addError(it) }

    private val getSetsFlow = getSets.execute(GetSets.Params(date))
        .map { it.getOrThrow() }
        .catch { addError(it) }

    private val  musclesTrainedFlow = getDailyReport.execute(GetDailyReport.Params(date = date.toDate()))
        .map { it.getOrThrow() }
        .map { it.musclesTrained.filter {
                it.isNotEmpty()
            }.toMuscles()
        }
        .catch { addError(it) }


    override val _uiState: StateFlow<WorkoutState> = combine(
        exercisesFlow,
        getSetsFlow,
        musclesTrainedFlow
    ) { exercises, sets, musclesTrained ->

        WorkoutState.Content(
            sets = sets,
            exercises = exercises,
            date = date,
            musclesTrained = musclesTrained
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = WorkoutState.Idle
    )


    init {
        on(WorkoutEvent.AddNewSet::class) {
            createNewSet.execute(CreateNewSet.Params(
                workoutDate = date,
                muscle = it.muscle,
                exercise = it.exercise
                )
            ).fold(
                onSuccess = {},
                onFailure = { addError(it) }
            )
        }


        on(WorkoutEvent.AddNewExercise::class) {

        }

        on(WorkoutEvent.DeleteSet::class) {
            deleteSet.execute(DeleteSet.Params(it.set)).fold(
                onSuccess = {},
                onFailure = { addError(it) }
            )
        }
    }

}






sealed interface WorkoutEvent {
    data class AddNewSet(val muscle: Muscle, val exercise: String): WorkoutEvent
    data class AddNewExercise(val muscle: Muscle, val exercise: String): WorkoutEvent
    data class DeleteSet(val set: SetDomainEntity): WorkoutEvent
}



sealed interface WorkoutState {

    object Idle: WorkoutState

    data class Content(
        val date: Long,
        val sets: List<SetDomainEntity>,
        val exercises: List<ExerciseDomainEntity>,
        val musclesTrained: List<Muscle>
    ): WorkoutState
}