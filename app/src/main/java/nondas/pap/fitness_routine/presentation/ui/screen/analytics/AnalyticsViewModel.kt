package nondas.pap.fitness_routine.presentation.ui.screen.analytics

import androidx.lifecycle.viewModelScope
import nondas.pap.fitness_routine.domain.entity.CardioDomainEntity
import nondas.pap.fitness_routine.domain.entity.SetDomainEntity
import nondas.pap.fitness_routine.domain.entity.WorkoutDomainEntity
import nondas.pap.fitness_routine.domain.entity.enums.Muscle
import nondas.pap.fitness_routine.domain.interactor.cardio.GetAllCardios
import nondas.pap.fitness_routine.domain.interactor.set.GetAllSets
import nondas.pap.fitness_routine.presentation.BlocViewModel
import nondas.pap.fitness_routine.presentation.util.toDate
import nondas.pap.fitness_routine.presentation.util.toTimeStamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    getAllSets: GetAllSets,
    getAllCardios: GetAllCardios
): BlocViewModel<AnalyticsEvent, AnalyticsState>()  {

    private val getAllSetsFlow = getAllSets.execute(Unit)
        .map { it.getOrThrow() }
        .catch { addError(it) }

    private val getCardiosFow = getAllCardios.execute(Unit)
        .map { it.getOrThrow() }
        .catch { addError(it) }

    private val showCardiosFlow = MutableSharedFlow<Boolean>()
    private val showGymSessionsFlow = MutableSharedFlow<Boolean>()

    private val fromDateFlow = MutableSharedFlow<String>()
    private val toDateFlow = MutableSharedFlow<String>()
    private val selectedMusclesFlow = MutableSharedFlow<List<Muscle>>()

    private val xxx = MutableSharedFlow<String>()

    override val _uiState: StateFlow<AnalyticsState> = combine(
        getAllSetsFlow.onStart { emit(listOf()) },
        getCardiosFow.onStart { emit(listOf()) },
        showCardiosFlow.onStart { emit(true) },
        showGymSessionsFlow.onStart { emit(true) },
        fromDateFlow.onStart { emit("") },
//        toDateFlow.onStart { emit(Date()) },
//        selectedMusclesFlow.onStart { emit(Muscle.entries.toList()) },
    ) { workoutSets, cardios, showCardios, showGymSessions, fromDate,  ->

        val cardiosByDate = cardios.groupBy { it.date }
        val setsByDate = workoutSets.groupBy { it.date.toDate() }
        val allDates = (cardiosByDate.keys + setsByDate.keys).toSet()

        val workouts = allDates.map { date ->
            val cardioList = cardiosByDate[date].orEmpty()
            val gymSession = setsByDate[date]
            Workout(
                date = date,
                cardios = cardioList,
                gymSession = gymSession
            )
        }

        AnalyticsState.Content(
            workouts = workouts,
            showCardios = showCardios,
            showWorkoutSessions = showGymSessions,
            fromDate = fromDate,
            toDate = "",
            selectedMuscles = Muscle.entries.toList()
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = AnalyticsState.Idle
    )


    init {

        on(AnalyticsEvent.GenerateReport::class) {
            // TODO: find a way to generate reports in pdf format.
        }

        on(AnalyticsEvent.SelectDateFrom::class) {
            fromDateFlow.emit(it.date)
        }

        on(AnalyticsEvent.SelectDateTo::class) {
            toDateFlow.emit(it.date)
        }

        on(AnalyticsEvent.ToggleGymSessions::class) {
            showGymSessionsFlow.emit(it.isChecked)
        }

        on(AnalyticsEvent.ToggleCardios::class) {
            showCardiosFlow.emit(it.isChecked)
        }

        on(AnalyticsEvent.SelectMuscle::class) {
            onState<AnalyticsState.Content> { state ->
                val selectedMuscles = state.selectedMuscles
                    .toMutableList()
                    .apply {
                        if (contains(it.muscle)) remove(it.muscle) else add(it.muscle)
                    }.toList()

                selectedMusclesFlow.emit(selectedMuscles)
            }
        }
    }

}

data class Workout(
    val date: Date,
    val cardios: List<CardioDomainEntity>?,
    val gymSession: List<SetDomainEntity>?
)

sealed interface AnalyticsEvent {
    object GenerateReport: AnalyticsEvent
    data class ToggleGymSessions(val isChecked: Boolean): AnalyticsEvent
    data class ToggleCardios(val isChecked: Boolean): AnalyticsEvent
    data class SelectDateFrom(val date: String): AnalyticsEvent
    data class SelectDateTo(val date: String): AnalyticsEvent
    data class SelectMuscle(val muscle: Muscle): AnalyticsEvent
}


sealed interface AnalyticsState {
    object Idle: AnalyticsState
    data class Content(
        val workouts: List<Workout>,
        val showCardios: Boolean,
        val showWorkoutSessions: Boolean,
        val fromDate: String,
        val toDate: String,
        val selectedMuscles: List<Muscle>
    ): AnalyticsState
}