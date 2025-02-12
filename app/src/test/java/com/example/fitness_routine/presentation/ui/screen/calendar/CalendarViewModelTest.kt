package com.example.fitness_routine.presentation.ui.screen.calendar

import com.example.fitness_routine.DummyEntities
import com.example.fitness_routine.dailyReport
import com.example.fitness_routine.domain.entity.enums.Choice
import com.example.fitness_routine.domain.interactor.report.GetDailyReports
import com.example.fitness_routine.domain.interactor.settings.ChangeChoice
import com.example.fitness_routine.domain.interactor.settings.GetSettings

import com.example.fitness_routine.presentation.ui.screen.MainDispatcherRule
import com.example.fitness_routine.presentation.ui.screen.onEvents
import com.example.fitness_routine.presentation.util.getDate
import com.example.fitness_routine.settings
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class CalendarViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var getDailyReports: GetDailyReports

    @Mock
    private lateinit var getSettings: GetSettings

    @Mock
    private lateinit var changeChoice: ChangeChoice

    private lateinit var viewModel: CalendarViewModel


    @Before
    fun setUp() = runTest {
        whenever(getDailyReports.execute(Unit)).thenReturn(flowOf(Result.success(reports)))
        whenever(getSettings.execute(Unit)).thenReturn(flowOf(Result.success(settings)))
    }


    @Test
    fun onFlowStart_loadReports() = runTest {
        initViewModel()

        onEvents(viewModel) { collectedStates ->
            assertEquals(
                listOf(
                    CalendarState.Idle,
                    defaultContent,
                    defaultContent.copy(reports, selectedChoice = Choice.valueOf(settings.choice))
                ),
                collectedStates
            )
        }
    }

    private fun initViewModel() {
        viewModel = CalendarViewModel(getDailyReports, getSettings, changeChoice)
    }

    companion object {
        val reports = (0..10).map {
            DummyEntities.dailyReport.copy(performedWorkout = true, date = Date())
        }

        val settings = DummyEntities.settings
    }

    private val defaultContent = CalendarState.Content(
        selectedChoice = Choice.valueOf(settings.choice),
        reports = listOf(),
        currentDate = getDate()
    )
}