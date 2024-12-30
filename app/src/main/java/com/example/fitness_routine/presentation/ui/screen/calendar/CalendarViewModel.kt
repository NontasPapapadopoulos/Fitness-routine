package com.example.fitness_routine.presentation.ui.screen.calendar

import androidx.lifecycle.viewModelScope
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.enums.Choice
import com.example.fitness_routine.domain.interactor.report.GetDailyReports
import com.example.fitness_routine.domain.interactor.settings.ChangeChoice
import com.example.fitness_routine.domain.interactor.settings.GetSettings
import com.example.fitness_routine.presentation.BlocViewModel
import com.example.fitness_routine.presentation.Handler
import com.example.fitness_routine.presentation.flatMapMergeWith
import com.example.fitness_routine.presentation.toResult
import com.example.fitness_routine.presentation.util.getDate
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
open class CalendarViewModel @Inject constructor(
    getDailyReports: GetDailyReports,
    getSettings: GetSettings,
    private val changeChoice: ChangeChoice
): BlocViewModel<CalendarEvent, CalendarState>() {

    private val currentDateFlow = MutableSharedFlow<String>()

    private val dailyReportsFlow = getDailyReports.execute(Unit)
        .map { it.getOrThrow() }
        .catch { addError(it) }

    private val choiceFlow = getSettings.execute(Unit)
        .map { it.getOrThrow() }
        .map { Choice.valueOf(it.choice) }

    override val _uiState: StateFlow<CalendarState> = combine(
        dailyReportsFlow.onStart { emit(listOf()) },
        currentDateFlow.onStart { emit(getDate()) },
        choiceFlow.onStart { emit(Choice.Workout) }
    ) { reports, currentDate, choice ->

        CalendarState.Content(
            reports = reports,
            currentDate = currentDate,
            selectedChoice = choice ?: Choice.Workout
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = CalendarState.Idle
    )


    init {
        on(CalendarEvent.SelectChoice::class) {
            changeChoice.execute(ChangeChoice.Params(it.choice)).fold(
                onSuccess = {},
                onFailure = { addError(it) }
            )
        }
    }
}


sealed interface CalendarEvent {
    data class SelectChoice(val choice: Choice): CalendarEvent
}



sealed interface CalendarState {

    object Idle: CalendarState

    data class Content(
        val reports: List<DailyReportDomainEntity>,
        val currentDate: String,
        val selectedChoice: Choice
    ): CalendarState

}

