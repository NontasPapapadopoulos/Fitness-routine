package com.example.fitness_routine.presentation.ui.screen.gym

import androidx.lifecycle.viewModelScope
import com.example.fitness_routine.domain.entity.CardioDomainEntity
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.interactor.cardio.GetAllCardios
import com.example.fitness_routine.domain.interactor.report.GetDailyReports
import com.example.fitness_routine.presentation.BlocViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class GymSessionsViewModel @Inject constructor(
    getDailyReports: GetDailyReports,
    getAllCardios: GetAllCardios,
): BlocViewModel<GymSessionsEvent, GymSessionsState>() {


    private val dailyReportsFlow = getDailyReports.execute(Unit)
        .map { it.getOrThrow() }
        .catch { addError(it) }

    private val cardiosFlow = getAllCardios.execute(Unit)
        .map { it.getOrThrow() }
        .catch { addError(it) }

    override val _uiState: StateFlow<GymSessionsState> = combine(
        dailyReportsFlow.onStart { emit(listOf()) },
        cardiosFlow.onStart { emit(listOf()) }
    ) { reports, cardios ->

        val cardiosByDate = cardios.groupBy { it.date }

        val workoutSessions = reports.map { report ->
            WorkoutSession(
                report = report,
                cardios = cardiosByDate[report.date] ?: emptyList()
            )
        }

        GymSessionsState.Content(
            workoutSessions = workoutSessions
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = GymSessionsState.Idle
    )

}





sealed interface GymSessionsEvent {

}


sealed interface GymSessionsState {
    object Idle : GymSessionsState
    data class Content(val workoutSessions: List<WorkoutSession>) : GymSessionsState
}


data class WorkoutSession(
    val report: DailyReportDomainEntity,
    val cardios: List<CardioDomainEntity>
)