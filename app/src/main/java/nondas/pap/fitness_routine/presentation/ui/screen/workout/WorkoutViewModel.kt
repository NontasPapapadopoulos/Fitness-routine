package nondas.pap.fitness_routine.presentation.ui.screen.workout

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import nondas.pap.fitness_routine.domain.entity.DailyReportDomainEntity
import nondas.pap.fitness_routine.domain.entity.ExerciseDomainEntity
import nondas.pap.fitness_routine.domain.entity.SetDomainEntity
import nondas.pap.fitness_routine.domain.entity.enums.Muscle
import nondas.pap.fitness_routine.domain.interactor.exercise.GetExercises
import nondas.pap.fitness_routine.domain.interactor.report.GetDailyReport
import nondas.pap.fitness_routine.domain.interactor.report.InitDailyReport
import nondas.pap.fitness_routine.domain.interactor.report.UpdateDailyReport
import nondas.pap.fitness_routine.domain.interactor.set.CreateNewSet
import nondas.pap.fitness_routine.domain.interactor.set.DeleteSet
import nondas.pap.fitness_routine.domain.interactor.set.GetSets
import nondas.pap.fitness_routine.domain.interactor.set.UpdateSet
import nondas.pap.fitness_routine.domain.interactor.settings.GetSettings
import nondas.pap.fitness_routine.domain.toMuscles
import nondas.pap.fitness_routine.presentation.BlocViewModel
import nondas.pap.fitness_routine.presentation.navigation.NavigationArgument
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
open class WorkoutViewModel @Inject constructor(
    private val getExercises: GetExercises,
    private val getSets: GetSets,
    private val getDailyReport: GetDailyReport,
    private val updateSet: UpdateSet,
    private val createNewSet: CreateNewSet,
    private val deleteSet: DeleteSet,
    private val updateReport: UpdateDailyReport,
    private val initDailyReport: InitDailyReport,
    private val getSettings: GetSettings,
    private val savedStateHandle: SavedStateHandle
): BlocViewModel<WorkoutEvent, WorkoutState>() {

    private val date get() = savedStateHandle.get<Long>(NavigationArgument.Date.param)!!

    private val exercisesFlow = getExercises.execute(Unit)
        .map { it.getOrThrow() }
        .catch { addError(it) }

    private val getSetsFlow = getSets.execute(GetSets.Params(date))
        .map { it.getOrThrow() }
        .catch { addError(it) }

    private val  dailyReport = getDailyReport.execute(GetDailyReport.Params(date = date))
        .map { it.getOrThrow() }
        .catch { addError(it) }

    private val settingsFlow = getSettings.execute(Unit)
        .map { it.getOrThrow() }
        .map { it.breakDuration }
        .catch { addError(it) }


    private val dialogFlow = MutableSharedFlow<Dialog?>()

    private val _navigateToExercisesFlow = MutableSharedFlow<Muscle>()
    open val navigateToExercisesFlow: SharedFlow<Muscle> =
        _navigateToExercisesFlow.asSharedFlow()


    @OptIn(ExperimentalCoroutinesApi::class)
    override val _uiState: StateFlow<WorkoutState> = combine(
        exercisesFlow,
        getSetsFlow,
        suspend { initDailyReport() }.asFlow().flatMapLatest { dailyReport },
        dialogFlow.onStart { emit(null) },
        settingsFlow
    ) { exercises, sets, dailyReport, dialog, breakTimeDuration ->

        WorkoutState.Content(
            sets = sets,
            exercises = exercises,
            date = date,
            musclesTrained = dailyReport.musclesTrained,
            dailyReport = dailyReport,
            dialog = dialog,
            breakTimeDuration = breakTimeDuration
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
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

        on(WorkoutEvent.DeleteSet::class) {
            deleteSet.execute(DeleteSet.Params(it.set)).fold(
                onSuccess = {},
                onFailure = { addError(it) }
            )
        }

        on(WorkoutEvent.SelectMuscle::class) {
            onState<WorkoutState.Content> { state ->
                val dailyReport = state.dailyReport
                val updatedMuscles = state.musclesTrained
                    .toMutableList()
                    .apply {
                        if (contains(it.muscle)) remove(it.muscle) else add(it.muscle)
                    }.toList()


                updateReport.execute(
                    UpdateDailyReport.Params(
                        dailyReport.copy(musclesTrained = updatedMuscles)
                    )
                )

            }
        }

        on(WorkoutEvent.ShowDialog::class) {
            dialogFlow.emit(it.dialog)
        }

        on(WorkoutEvent.DismissDialog::class) {
            dialogFlow.emit(null)
        }

        on(WorkoutEvent.NavigateToExercises::class) {
            _navigateToExercisesFlow.emit(it.muscle)
        }

        on(WorkoutEvent.UpdateSet::class) {
            println("updated set: ${it.set}")
            val set = when(it.field) {
                SetField.Weight -> { it.set.copy(weight = it.value) }
                SetField.Repeat -> { it.set.copy(repeats = it.value)  }
            }

            updateSet.execute(UpdateSet.Params(set = set)).fold(
                onSuccess = {},
                onFailure = { addError(it) }
            )
        }
    }

    private suspend fun initDailyReport() {
        initDailyReport.execute(InitDailyReport.Params(date)).fold(
            onSuccess = {}, onFailure = { addError(it) }
        )
    }

}

sealed interface Dialog {
    data class AddExercise(val muscle: Muscle): Dialog
    object Break: Dialog
}




sealed interface WorkoutEvent {
    data class AddNewSet(val muscle: Muscle, val exercise: String): WorkoutEvent
    data class AddNewExercise(val muscle: Muscle, val exercise: String): WorkoutEvent
    data class DeleteSet(val set: SetDomainEntity): WorkoutEvent
    data class UpdateSet(val set: SetDomainEntity, val field: SetField, val value: String): WorkoutEvent
    data class SelectMuscle(val muscle: Muscle): WorkoutEvent
    data class NavigateToExercises(val muscle: Muscle): WorkoutEvent
    data class ShowDialog(val dialog: Dialog?): WorkoutEvent

    object DismissDialog: WorkoutEvent

}

enum class SetField {
    Weight,
    Repeat
}

sealed interface WorkoutState {

    object Idle: WorkoutState

    data class Content(
        val date: Long,
        val sets: List<SetDomainEntity>,
        val exercises: List<ExerciseDomainEntity>,
        val musclesTrained: List<Muscle>,
        val dailyReport: DailyReportDomainEntity,
        val dialog: Dialog?,
        val breakTimeDuration: String
    ): WorkoutState
}