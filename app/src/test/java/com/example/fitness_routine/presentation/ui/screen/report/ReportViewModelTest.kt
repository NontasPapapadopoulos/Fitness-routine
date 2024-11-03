package com.example.fitness_routine.presentation.ui.screen.report

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitness_routine.DummyEntities
import com.example.fitness_routine.dailyReport
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.interactor.report.AddDailyReport
import com.example.fitness_routine.domain.interactor.report.DeleteDailyReport
import com.example.fitness_routine.domain.interactor.report.GetDailyReport
import com.example.fitness_routine.domain.interactor.report.InitDailyReport
import com.example.fitness_routine.domain.interactor.report.UpdateDailyReport
import com.example.fitness_routine.presentation.navigation.NavigationArgument
import com.example.fitness_routine.presentation.ui.screen.MainDispatcherRule
import com.example.fitness_routine.presentation.ui.screen.onEvents
import com.example.fitness_routine.presentation.util.toTimeStamp
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import com.example.fitness_routine.presentation.ui.screen.onEvents

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
class ReportViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ReportViewModel

    @Mock
    private lateinit var getDailyReport: GetDailyReport

    @Mock
    private lateinit var deleteReport: DeleteDailyReport

    @Mock
    private lateinit var updateDailyReport: UpdateDailyReport

    @Mock
    private lateinit var createReport: AddDailyReport

    @Mock
    private lateinit var initDailyReport: InitDailyReport

    private val savedStateHandle: SavedStateHandle =
        SavedStateHandle(mapOf(NavigationArgument.Date.param to date))

    @Before
    fun setUp() = runTest{
        whenever(getDailyReport.execute(any())).thenReturn(flowOf(Result.success(report)))
        whenever(deleteReport.execute(any())).thenReturn(Result.success(Unit))
        whenever(updateDailyReport.execute(any())).thenReturn(Result.success(Unit))
        whenever(createReport.execute(any())).thenReturn(Result.success(Unit))
        whenever(initDailyReport.execute(any())).thenReturn(Result.success(Unit))
    }

    @Test
    fun onFlowStart_loadDailyReport() = runTest {
        initViewModel()

        onEvents(viewModel) { collectedStates ->
            assertEquals(
                listOf(
                    ReportState.Idle,
                    defaultContent
                ),
                collectedStates
            )
        }
    }

    @Test
    fun onUpdateCheckBox_togglesCheckBox() = runTest {
        whenever(getDailyReport.execute(any())).thenReturn(flowOf(Result.success(report.copy(
            performedWorkout = true,
            hadCreatine = true,
            hadCheatMeal = true
        ))))

        initViewModel()


        onEvents(
            viewModel,
            ReportEvent.UpdateCheckBox(isChecked = true, CheckBoxField.Workout),
            ReportEvent.UpdateCheckBox(isChecked = true, CheckBoxField.Creatine),
            ReportEvent.UpdateCheckBox(isChecked = true, CheckBoxField.CheatMeal),
        ) { collectedStates ->

            assertEquals(
                listOf(
                    ReportState.Idle,
                    defaultContent,
                    defaultContent.copy(dailyReport = report.copy(performedWorkout = !report.performedWorkout)),
                    defaultContent.copy(dailyReport = report.copy(hadCreatine = !report.hadCreatine)),
                    defaultContent.copy(dailyReport = report.copy(hadCheatMeal = !report.hadCheatMeal)),
                ),
                collectedStates
            )
        }
    }


    @Test
    fun onSelectMuscle_selectsMuscleGroups() = runTest {

    }


    private fun initViewModel() {
        viewModel = ReportViewModel(
            getDailyReport,
            deleteReport,
            updateDailyReport,
            createReport,
            savedStateHandle,
            initDailyReport
        )
    }


    companion object {
        val report = DummyEntities.dailyReport
        val date = Date().toTimeStamp()

    }


    private val  defaultContent = ReportState.Content(
        date = date,
        dailyReport = DummyEntities.dailyReport
    )

}

