package nondas.pap.fitness_routine.presentation.ui.screen.calendar

import kotlinx.coroutines.flow.MutableStateFlow
import nondas.pap.fitness_routine.DummyEntities
import nondas.pap.fitness_routine.dailyReport
import nondas.pap.fitness_routine.domain.entity.DailyReportDomainEntity
import nondas.pap.fitness_routine.domain.entity.enums.Choice
import nondas.pap.fitness_routine.domain.interactor.report.GetDailyReports
import nondas.pap.fitness_routine.domain.interactor.settings.ChangeChoice
import nondas.pap.fitness_routine.domain.interactor.settings.GetSettings

import nondas.pap.fitness_routine.presentation.ui.screen.MainDispatcherRule
import nondas.pap.fitness_routine.presentation.ui.screen.onEvents
import nondas.pap.fitness_routine.presentation.util.getDate
import nondas.pap.fitness_routine.settings
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import nondas.pap.fitness_routine.InlineClassesAnswer
import nondas.pap.fitness_routine.domain.entity.SettingsDomainEntity
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

    private val settingsFlow: MutableStateFlow<Result<SettingsDomainEntity>> =
        MutableStateFlow(Result.success(settings))

    @Before
    fun setUp() = runTest  {
        whenever(getDailyReports.execute(any())).thenReturn(flowOf(Result.success(reports)))
        whenever(getSettings.execute(any())).thenReturn(settingsFlow)

        whenever(changeChoice.execute(any())).thenAnswer(InlineClassesAnswer { invocation ->
            val params = invocation.getArgument<ChangeChoice.Params>(0)
            val settings = settingsFlow.value.getOrThrow()

            val newSettings = settings.copy(
                choice = params.choice
            )

            emitSettings(newSettings)
            Result.success(Unit)
        })
    }


    @Test
    fun onFlowStart_loadReports() = runTest {
        initViewModel()

        onEvents(viewModel) { collectedStates ->
            assertEquals(
                listOf(
                    CalendarState.Idle,
                    defaultContent,
                    defaultContent.copy(reports = reports, selectedChoice = Choice.Creatine)
                ),
                collectedStates
            )
        }
    }

    @Test
    fun onSelectChoice_selectedDaysChange() = runTest {
        initViewModel()

        val newChoice = Choice.Cheat
        onEvents(
            viewModel,
            CalendarEvent.SelectChoice(newChoice)
        ) { collectedStates ->
            assertEquals(
                listOf(
                    defaultContent.copy(reports = reports, selectedChoice = newChoice),
                ),
                collectedStates
            )
        }
    }


    private fun emitSettings(settings: SettingsDomainEntity) = runBlocking {
        settingsFlow.emit(Result.success(settings))
    }

    companion object {
        val reports = buildList {
            add(DummyEntities.dailyReport)
            add(DummyEntities.dailyReport)
            add(DummyEntities.dailyReport)
        }


        val settings = DummyEntities.settings.copy(choice = Choice.Creatine)

        private val defaultContent = CalendarState.Content(
            selectedChoice = Choice.Workout,
            reports = listOf(),
            currentDate = getDate()
        )
    }


    private fun initViewModel() {
        viewModel = CalendarViewModel(getDailyReports, getSettings, changeChoice)
    }

}