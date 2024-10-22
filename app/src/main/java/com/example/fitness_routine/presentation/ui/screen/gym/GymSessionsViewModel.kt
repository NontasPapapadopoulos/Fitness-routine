package com.example.fitness_routine.presentation.ui.screen.gym

import androidx.lifecycle.viewModelScope
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.interactor.report.GetDailyReports
import com.example.fitness_routine.presentation.BlocViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class GymSessionsViewModel @Inject constructor(
    getDailyReports: GetDailyReports
): BlocViewModel<GymSessionsEvent, GymSessionsState>() {


    private val dailyReportsFlow = getDailyReports.execute(Unit)
        .map { it.getOrThrow() }
        .catch { addError(it) }


    override val _uiState: StateFlow<GymSessionsState> = dailyReportsFlow.map {
        GymSessionsState.Content(
            dailyReports = it
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
    data class Content(val dailyReports: List<DailyReportDomainEntity>) : GymSessionsState
}