package com.example.fitness_routine.presentation.ui.screen.cheat

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
class CheatMealsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var getDailyReports: GetDailyReports

    private lateinit var viewModel: CheatMealsViewModel

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
                    CheatMealsState.Idle,
                    defaultContent.copy(reports)
                ),
                collectedStates
            )
        }
    }



    private fun initViewModel() {
        viewModel = CheatMealsViewModel(getDailyReports)
    }



    companion object {
        val reports = (0..10).map {
            DummyEntities.dailyReport.copy(hadCheatMeal = true, date = Date())
        }
    }

    private val defaultContent = CheatMealsState.Content(
        dailyReports = listOf()
    )
}