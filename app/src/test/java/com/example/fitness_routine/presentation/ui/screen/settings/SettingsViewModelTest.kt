package com.example.fitness_routine.presentation.ui.screen.settings

import com.example.fitness_routine.DummyEntities
import com.example.fitness_routine.domain.entity.enums.Choice
import com.example.fitness_routine.domain.interactor.settings.ChangeSettings
import com.example.fitness_routine.domain.interactor.settings.GetSettings
import com.example.fitness_routine.presentation.ui.screen.MainDispatcherRule
import com.example.fitness_routine.presentation.ui.screen.onEvents
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
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class SettingsViewModelTest {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: SettingsViewModel

    @Mock
    private lateinit var getSettings: GetSettings

    @Mock
    private lateinit var changeSettings: ChangeSettings


    @Before
    fun setUp() = runTest {
        whenever(getSettings.execute(Unit)).thenReturn(flowOf(Result.success(settings)))
        whenever(changeSettings.execute(any())).thenReturn(Result.success(Unit))
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