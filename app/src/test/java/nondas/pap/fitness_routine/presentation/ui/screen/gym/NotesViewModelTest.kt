package com.example.fitness_routine.presentation.ui.screen.gym

import com.example.fitness_routine.DummyEntities
import com.example.fitness_routine.dailyReport
import com.example.fitness_routine.domain.entity.NoteDomainEntity
import com.example.fitness_routine.domain.interactor.note.GetAllNotes
import com.example.fitness_routine.domain.interactor.report.GetDailyReports
import com.example.fitness_routine.note
import com.example.fitness_routine.presentation.ui.screen.MainDispatcherRule
import com.example.fitness_routine.presentation.ui.screen.notes.NotesState
import com.example.fitness_routine.presentation.ui.screen.notes.NotesViewModel
import com.example.fitness_routine.presentation.ui.screen.onEvents
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class NotesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var getAllNotes: GetAllNotes

    private lateinit var viewModel: NotesViewModel

    @Before
    fun setUp() {
        whenever(getAllNotes.execute(any())).thenReturn(flowOf(Result.success(notes)))
    }


    @Test
    fun onFlowStart_loadNotes() = runTest {

        initViewModel()

        onEvents(viewModel) { collectedStates ->
            assertEquals(
                listOf(
                    NotesState.Idle,
                    defaultContent.copy(notes)
                ),
                collectedStates
            )
        }
    }



    private fun initViewModel() {
        viewModel = NotesViewModel(getAllNotes)
    }

    companion object {
        val notes = buildList<NoteDomainEntity> {
            DummyEntities.note
            DummyEntities.note
            DummyEntities.note
        }

        private val defaultContent = NotesState.Content(
            notes = notes
        )

    }


}