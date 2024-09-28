package com.example.fitness_routine.presentation.screen.calendar

import com.example.fitness_routine.presentation.util.Calendar
import com.example.fitness_routine.presentation.BlocViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class CalendarViewModel @Inject constructor(

): BlocViewModel<Calendar, CalendarState>(){


    override val _uiState: StateFlow<CalendarState>
        get() = TODO("Not yet implemented")


}


sealed interface CalendarEvent {



}



sealed interface CalendarState {

    object Idle: CalendarState

    data class Content(
        val xx: String
    ): CalendarState


}