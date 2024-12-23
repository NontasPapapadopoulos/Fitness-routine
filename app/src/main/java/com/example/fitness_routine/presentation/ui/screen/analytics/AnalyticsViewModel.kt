package com.example.fitness_routine.presentation.ui.screen.analytics

import androidx.lifecycle.viewModelScope
import com.example.fitness_routine.domain.entity.CardioDomainEntity
import com.example.fitness_routine.domain.entity.WorkoutDomainEntity
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.domain.interactor.GetWorkouts
import com.example.fitness_routine.domain.interactor.cardio.GetAllCardios
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
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    getWorkouts: GetWorkouts,
    getAllCardios: GetAllCardios
): BlocViewModel<AnalyticsEvent, AnalyticsState>()  {

    private val getWorkoutsFlow = getWorkouts.execute(Unit)
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
        getWorkoutsFlow.onStart { emit(listOf()) },
        getCardiosFow.onStart { emit(listOf()) },
        showCardiosFlow.onStart { emit(true) },
        showGymSessionsFlow.onStart { emit(true) },
        fromDateFlow.onStart { emit("") },
//        toDateFlow.onStart { emit(Date()) },
//        selectedMusclesFlow.onStart { emit(Muscle.entries.toList()) },
    ) { workouts, cardios, showCardios, showGymSessions, fromDate,  ->


        AnalyticsState.Content(
            workoutSessions = workouts,
            cardios = cardios,
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
        val workoutSessions: List<WorkoutDomainEntity>,
        val cardios: List<CardioDomainEntity>,
        val showCardios: Boolean,
        val showWorkoutSessions: Boolean,
        val fromDate: String,
        val toDate: String,
        val selectedMuscles: List<Muscle>
    ): AnalyticsState
}