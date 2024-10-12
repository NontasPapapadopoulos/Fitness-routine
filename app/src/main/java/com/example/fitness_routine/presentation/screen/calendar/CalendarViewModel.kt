package com.example.fitness_routine.presentation.screen.calendar

import androidx.lifecycle.viewModelScope
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.interactor.report.GetDailyReports
import com.example.fitness_routine.presentation.BlocViewModel
import com.example.fitness_routine.presentation.util.getCurrentDate
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
class CalendarViewModel @Inject constructor(
    getDailyReports: GetDailyReports
): BlocViewModel<CalendarEvent, CalendarState>() {

    private val currentDateFlow = MutableSharedFlow<String>()
    private val choiceFlow = MutableSharedFlow<Choice>()

    private val dailyReportsFlow = getDailyReports.execute(Unit)
        .map { it.getOrThrow() }
        .catch { addError(it) }


    override val _uiState: StateFlow<CalendarState> = combine(
        dailyReportsFlow.onStart { emit(listOf()) },
        currentDateFlow.onStart { emit(getCurrentDate()) },
        choiceFlow.onStart { emit(Choice.Workout) }
    ) { reports, currentDate, choice ->

        CalendarState.Content(
            reports = reports,
            currentDate = currentDate,
            selectedChoice = choice
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = CalendarState.Idle
    )


    init {
        on(CalendarEvent.SelectChoice::class) {
            choiceFlow.emit(it.choice)
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


enum class Choice(val value: String) {
    Workout("Performed Workout"),
    Creatine("Had Creatine"),
    Cheat("Had a Cheat meal")
}