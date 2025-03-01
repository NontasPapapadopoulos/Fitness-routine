package nondas.pap.fitness_routine.presentation.ui.screen.settings

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import nondas.pap.fitness_routine.domain.entity.SettingsDomainEntity
import nondas.pap.fitness_routine.domain.entity.enums.Choice
import nondas.pap.fitness_routine.presentation.component.AppSurface
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(AndroidJUnit4::class)
class SettingsScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setUp() {
        whenever(viewModel.errorFlow).thenReturn(MutableSharedFlow())
    }


    @Test
    fun contentState_whenUpdatingBreakDurationTextField_addsTextChanged() {
        // given
        whenever(viewModel.uiState).thenReturn(
            MutableStateFlow(SettingsState.Content(settings))
        )

        composeTestRule.setContent {
            AppSurface {
                SettingsScreen(viewModel = viewModel, navigateBack = {})
            }
        }


        // when
        val input = "60"
        composeTestRule.onNodeWithTag(SettingsScreenConstants.BREAK_DURATION_TEXT_FIELD).performTextInput(input)

        //then
        verify(viewModel).add(SettingsEvent.TextChanged(input))
    }


    @Test
    fun contentState_whenSelectingChoice_addsSelectChoice() {
        // given
        whenever(viewModel.uiState).thenReturn(
            MutableStateFlow(SettingsState.Content(settings))
        )

        composeTestRule.setContent {
            AppSurface {
                SettingsScreen(viewModel = viewModel, navigateBack = {})
            }
        }


        // when
        val choice = Choice.Creatine
        composeTestRule.onNodeWithTag(SettingsScreenConstants.CHOICE_RADIO_BUTTON + choice.name).performClick()

        // then
        verify(viewModel).add(SettingsEvent.SelectChoice(choice))
    }


    @Test
    fun contentState_whenClickingSwitch_addsToggleDarkMode() {
        // given
        whenever(viewModel.uiState).thenReturn(
            MutableStateFlow(SettingsState.Content(settings))
        )

        composeTestRule.setContent {
            AppSurface {
                SettingsScreen(viewModel = viewModel, navigateBack = {})
            }
        }


        // when
        composeTestRule.onNodeWithTag(SettingsScreenConstants.DARK_MODE_SWITCH).performClick()

        // then
        verify(viewModel).add(SettingsEvent.ToggleDarkMode)
    }




    companion object {
        val settings = SettingsDomainEntity(
            isDarkModeEnabled = true,
            breakDuration = "",
            choice = Choice.Workout.name
        )
    }
}