package nondas.pap.fitness_routine.presentation.ui.screen.settings

import nondas.pap.fitness_routine.DummyEntities
import nondas.pap.fitness_routine.InlineClassesAnswer
import nondas.pap.fitness_routine.domain.entity.SettingsDomainEntity
import nondas.pap.fitness_routine.domain.entity.enums.Choice
import nondas.pap.fitness_routine.domain.interactor.settings.ChangeSettings
import nondas.pap.fitness_routine.domain.interactor.settings.GetSettings
import nondas.pap.fitness_routine.presentation.ui.screen.MainDispatcherRule
import nondas.pap.fitness_routine.presentation.ui.screen.onEvents
import nondas.pap.fitness_routine.settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
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

@RunWith(MockitoJUnitRunner::class)
class SettingsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: SettingsViewModel

    @Mock
    private lateinit var getSettings: GetSettings

    @Mock
    private lateinit var changeSettings: ChangeSettings

    private val settingsFlow: MutableStateFlow<Result<SettingsDomainEntity>> =
        MutableStateFlow(Result.success(settings))

    @Before
    fun setUp() = runTest {
        whenever(getSettings.execute(Unit)).thenReturn(flowOf(Result.success(settings)))

    }

    private fun emitSettings(settings: SettingsDomainEntity) = runBlocking {
        settingsFlow.emit(Result.success(settings))
    }


    @Test
    fun onFlowStart_returnsSettings() = runTest {
        initViewModel()
        onEvents(
            viewModel
        ) { collectedStates ->
            assertEquals(
                listOf(
                    SettingsState.Idle,
                    defaultContent
                ),
                collectedStates
            )
        }
    }

    @Test
    fun onToggleSwitchButton_changesDarkMode() = runTest {
        whenever(changeSettings.execute(any())).thenAnswer(InlineClassesAnswer { invocation ->
            val params = invocation.getArgument<ChangeSettings.Params>(0)
            val settings = settingsFlow.value.getOrThrow()
            emitSettings(settings.copy(isDarkModeEnabled = params.settings.isDarkModeEnabled))
            Result.success(Unit)
        })

        initViewModel()

        onEvents(
            viewModel,
            SettingsEvent.ToggleDarkMode
        ) { collectedStates ->
            assertEquals(
                listOf(
                    SettingsState.Idle,
                    defaultContent,
                    defaultContent.copy(settings.copy(isDarkModeEnabled = !defaultContent.settings.isDarkModeEnabled))
                ),
                collectedStates
            )
        }
    }


    @Test
    fun onTextChanged_changesBreakDuration() = runTest {
        whenever(changeSettings.execute(any())).thenAnswer( InlineClassesAnswer { invocation ->
            val params = invocation.getArgument<ChangeSettings.Params>(0)
            val settings = settingsFlow.value.getOrThrow()
            emitSettings(settings.copy(breakDuration = params.settings.breakDuration))
            Result.success(Unit)
        })

        initViewModel()

        onEvents(
            viewModel,
            SettingsEvent.TextChanged("text")
        ) { collectedStates ->
            assertEquals(
                listOf(
                    SettingsState.Idle,
                    defaultContent,
                    defaultContent.copy(settings.copy(breakDuration = "text"))
                ),
                collectedStates
            )
        }
    }

    @Test
    fun onSelectChoice_changesChoice() = runTest {
        whenever(changeSettings.execute(any())).thenAnswer( InlineClassesAnswer { invocation ->
            val params = invocation.getArgument<ChangeSettings.Params>(0)
            val settings = settingsFlow.value.getOrThrow()
            emitSettings(settings.copy(choice = params.settings.choice))
            Result.success(Unit)
        })

        initViewModel()

        onEvents(
            viewModel,
            SettingsEvent.SelectChoice(Choice.Creatine)
        ) { collectedStates ->
            assertEquals(
                listOf(
                    SettingsState.Idle,
                    defaultContent,
                    defaultContent.copy(settings.copy(choice = Choice.Creatine.name))
                ),
                collectedStates
            )
        }
    }


    private fun initViewModel() {
        viewModel = SettingsViewModel(getSettings, changeSettings)
    }


    companion object {
        val settings = DummyEntities.settings.copy(
            choice = Choice.Workout.name,
            isDarkModeEnabled = true,
            breakDuration = ""
        )
    }

    private val defaultContent = SettingsState.Content(
        settings = settings
    )
}