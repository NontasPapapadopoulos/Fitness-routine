package com.example.fitness_routine.presentation.ui.screen.notes

import androidx.lifecycle.viewModelScope
import com.example.fitness_routine.domain.entity.CardioDomainEntity
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.NoteDomainEntity
import com.example.fitness_routine.domain.interactor.cardio.GetAllCardios
import com.example.fitness_routine.domain.interactor.note.GetAllNotes
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
class NotesViewModel @Inject constructor(
    private val getAllNotes: GetAllNotes
) : BlocViewModel<NotesEvent, NotesState>() {


    override val _uiState: StateFlow<NotesState> = getAllNotes.execute(Unit)
        .map { it.getOrThrow() }
        .onStart { emit(listOf()) }
        .catch { addError(it) }
        .map { notes ->

            NotesState.Content(
                notes = notes
                    .filter { it.note.isNotEmpty() }
                    .sortedBy { it.date }
            )

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = NotesState.Idle
        )

}


sealed interface NotesEvent {

}


sealed interface NotesState {
    object Idle : NotesState
    data class Content(val notes: List<NoteDomainEntity>) : NotesState
}


