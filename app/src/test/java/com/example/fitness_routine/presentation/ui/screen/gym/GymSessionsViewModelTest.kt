package com.example.fitness_routine.presentation.ui.screen.gym

import com.example.fitness_routine.DummyEntities
import com.example.fitness_routine.dailyReport
import com.example.fitness_routine.domain.interactor.report.GetDailyReports
import com.example.fitness_routine.presentation.ui.screen.MainDispatcherRule
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
import org.mockito.kotlin.whenever
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class GymSessionsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var getDailyReports: GetDailyReports

    private lateinit var viewModel: GymSessionsViewModel

    @Before
    fun setUp() {
        whenever(getDailyReports.execute(Unit)).thenReturn(flowOf(Result.success(reports)))
    }


    @Test
    fun onFlowStart_loadCheatMeals() = runTest {
        initViewModel()

        onEvents(viewModel) { collectedStates ->
            assertEquals(
                listOf(
                    GymSessionsState.Idle,
                    defaultContent.copy(reports)
                ),
                collectedStates
            )
        }
    }



    private fun initViewModel() {
        viewModel = GymSessionsViewModel(getDailyReports)
    }

    companion object {
        val reports = (0..10).map {
            DummyEntities.dailyReport.copy(performedWorkout = true, date = Date())
        }
    }

    private val defaultContent = GymSessionsState.Content(
        dailyReports = listOf()
    )
}