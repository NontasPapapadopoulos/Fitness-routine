package nondas.pap.fitness_routine.presentation.ui.screen.calendar

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import nondas.pap.fitness_routine.domain.entity.enums.Choice
import nondas.pap.fitness_routine.presentation.component.AppSurface
import nondas.pap.fitness_routine.presentation.util.toTimeStamp
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
import java.util.Date

@RunWith(AndroidJUnit4::class)
class CalendarScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()


    @Mock
    private lateinit var viewModel: CalendarViewModel


    @Before
    fun setUp() {
        whenever(viewModel.errorFlow).thenReturn(MutableSharedFlow())
    }


    @Test
    fun onContentState_whenChoiceIsClicked_addsSelectChoice() {
        // given
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(defaultContent))

        composeTestRule.setContent {
            AppSurface {
                CalendarScreen(viewModel = viewModel, navigateToScreen = {}, navigateToDailyReport = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(CalendarScreenConstants.CHOICE_BUTTON+Choice.Cheat)
            .performClick()


        // then
        verify(viewModel).add(CalendarEvent.SelectChoice(Choice.Cheat))
    }

    @Test
    fun onContentState_whenScreenIsLaunched_DisplaysTheCurrentMonth() {
        // given
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(defaultContent))

        composeTestRule.setContent {
            AppSurface {
                CalendarScreen(viewModel = viewModel, navigateToScreen = {}, navigateToDailyReport = {})
            }
        }

        // Since the current date is 03/03/2025, we expect the month to be Mar
        composeTestRule.onNodeWithText("Mon, Mar 3").assertIsDisplayed()

    }

    @Test
    fun onContentState_whenScreenIsLaunched_DisplaysAllDaysOfTheCurrentMonth() {
        // given
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(defaultContent))

        composeTestRule.setContent {
            AppSurface {
                CalendarScreen(viewModel = viewModel, navigateToScreen = {}, navigateToDailyReport = {})
            }
        }

        // Since the current date is 03/03/2025, we expect 31 days
        (1..31).map {
            composeTestRule.onNodeWithText(it.toString()).assertIsDisplayed()
                .assertHasClickAction()
        }

    }

    companion object {

        val defaultContent = CalendarState.Content(
            reports = listOf(),
            currentDate = "03/03/2025",
            selectedChoice = Choice.Workout
        )
    }


}