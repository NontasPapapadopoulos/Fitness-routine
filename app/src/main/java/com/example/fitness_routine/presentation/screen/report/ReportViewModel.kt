package com.example.fitness_routine.presentation.screen.report

import com.example.fitness_routine.presentation.BlocViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class ReportViewModel @Inject constructor(

): BlocViewModel<ReportEvent, ReportState>() {


    override val _uiState: StateFlow<ReportState>
        get() = TODO("Not yet implemented")

}


sealed interface ReportEvent {

}


sealed interface ReportState {

    object Idle: ReportState

    data class Content(
        val xxx: String
    ): ReportState

}