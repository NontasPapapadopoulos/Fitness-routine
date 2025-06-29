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
import nondas.pap.fitness_routine.presentation.ui.screen.calendar.CalendarScreenConstants.Companion.SIDE_MENU_BUTTON
import nondas.pap.fitness_routine.presentation.ui.screen.sidemenu.SideMenuConstants.Companion.SIDE_MENU
import nondas.pap.fitness_routine.presentation.util.Calendar
import nondas.pap.fitness_routine.presentation.util.getCurrentDay
import nondas.pap.fitness_routine.presentation.util.getCurrentMonth
import nondas.pap.fitness_routine.presentation.util.getCurrentYear
import nondas.pap.fitness_routine.presentation.util.getDayOfWeek
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
        val currentMonth = getCurrentMonth()
        val currentDay = getCurrentDay()

        val text = "${getDayOfWeek().take(3)}, ${currentMonth.take(3)} $currentDay"

        // Since the current date is 03/03/2025, we expect the month to be Mar
        composeTestRule.onNodeWithText(text).assertIsDisplayed()

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

        val currentYear = getCurrentYear().toInt()
        val calendar = Calendar().createCalendar(currentYear-1 , currentYear+1)
        val currentMonth = getCurrentMonth()


        val numberOfDays = calendar.years
            .find { it.year == currentYear }?.months
            ?.find { it.monthName == currentMonth }
            ?.days?.size!!


        (1..numberOfDays).map {
            composeTestRule.onNodeWithText(it.toString()).assertIsDisplayed()
                .assertHasClickAction()
        }

    }

    @Test
    fun onContentState_whenSideMenuButtonIsClicked_sideMenuIsDisplayed() {
        // given
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(defaultContent))

        composeTestRule.setContent {
            AppSurface {
                CalendarScreen(viewModel = viewModel, navigateToScreen = {}, navigateToDailyReport = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(SIDE_MENU_BUTTON).performClick()

        //then

        composeTestRule.onNodeWithTag(SIDE_MENU).assertIsDisplayed()


    }

    companion object {

        val defaultContent = CalendarState.Content(
            reports = listOf(),
            currentDate = "03/03/2025",
            selectedChoice = Choice.Workout
        )
    }


}