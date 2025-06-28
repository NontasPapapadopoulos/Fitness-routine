package nondas.pap.fitness_routine.presentation.navigation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import junit.framework.TestCase.assertTrue
import nondas.pap.fitness_routine.presentation.HiltComponentActivity
import nondas.pap.fitness_routine.presentation.ui.screen.calendar.CalendarScreenConstants.Companion.CALENDAR_BUTTON
import nondas.pap.fitness_routine.presentation.ui.screen.calendar.CalendarScreenConstants.Companion.CALENDAR_CONTENT
import nondas.pap.fitness_routine.presentation.ui.screen.calendar.CalendarScreenConstants.Companion.CHEAT_BUTTON
import nondas.pap.fitness_routine.presentation.ui.screen.calendar.CalendarScreenConstants.Companion.GYM_BUTTON
import nondas.pap.fitness_routine.presentation.ui.screen.calendar.CalendarScreenConstants.Companion.SIDE_MENU_BUTTON
import nondas.pap.fitness_routine.presentation.ui.screen.calendar.CalendarScreenConstants.Companion.WORKOUT_BUTTON
import nondas.pap.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.BODY_MEASUREMENT_BUTTON
import nondas.pap.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.NUTRITION_CONTENT
import nondas.pap.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.NUTRITION_TAB
import nondas.pap.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.WORKOUT_BUTTON_REPORT
import nondas.pap.fitness_routine.presentation.ui.screen.sidemenu.SideMenuConstants.Companion.MEASUREMENTS_BUTTON
import nondas.pap.fitness_routine.presentation.ui.screen.sidemenu.SideMenuConstants.Companion.NOTES_BUTTON
import nondas.pap.fitness_routine.presentation.ui.screen.sidemenu.SideMenuConstants.Companion.SETTINGS_BUTTON
import nondas.pap.fitness_routine.presentation.ui.screen.sidemenu.SideMenuConstants.Companion.SIDE_MENU
import nondas.pap.fitness_routine.presentation.ui.theme.AppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    lateinit var navController: TestNavHostController



    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(composeTestRule.activity)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppTheme {
                RootNavGraph(navController = navController)
            }
        }

    }


    @Test
    fun onStart_showsSplashScreen() {
        assertTrue(navController.currentBackStackEntry?.destination?.hasRoute<Splash>() ?: false)
       // composeTestRule.onNodeWithTag(SPLASH_SCREEN_TAG).assertIsDisplayed()
    }


    @Test
    fun onStart_showsCalendarScreen_afterSplashScreen() {
        composeTestRule.waitUntil(timeoutMillis = 2000L) {
            navController.currentBackStackEntry?.destination?.hasRoute<Calendar>() == true
        }

        assertTrue(navController.currentBackStackEntry?.destination?.hasRoute<Calendar>() ?: false)

    }


    @Test
    fun onClickCheatButton_navigatesToCheatMealsScreen() {

        waitUntilComposableShowUp(CHEAT_BUTTON)

        composeTestRule.onNodeWithTag(CHEAT_BUTTON).performClick()
        assertTrue(navController.currentBackStackEntry?.destination?.hasRoute<Cheat>() ?: false)
    }


//    @Test
//    fun onClickGymButton_navigatesToGymSessionsScreen() {
//
//        waitUntilComposableShowUp(CALENDAR_CONTENT)
//
//        composeTestRule.onNodeWithTag(GYM_BUTTON).performClick()
//        assertTrue(navController.currentBackStackEntry?.destination?.hasRoute<GymSessions>() ?: false)
//    }

    @Test
    fun onClickWorkoutButton_navigatesToWorkoutScreen() {

        waitUntilComposableShowUp(WORKOUT_BUTTON)

        composeTestRule.onNodeWithTag(WORKOUT_BUTTON).performClick()
        assertTrue(navController.currentBackStackEntry?.destination?.hasRoute<Workout>() ?: false)
    }

    @Test
    fun onClickCalendarButton_navigatesToCalendarScreen() {
        waitUntilComposableShowUp(WORKOUT_BUTTON)

        composeTestRule.onNodeWithTag(WORKOUT_BUTTON).performClick()
        composeTestRule.onNodeWithTag(CALENDAR_BUTTON).performClick()
        assertTrue(navController.currentBackStackEntry?.destination?.hasRoute<Calendar>() ?: false)
    }


    @Test
    fun onClickSettingsButton_navigatesToSettingsScreen() {
        waitUntilComposableShowUp(CALENDAR_CONTENT)
        composeTestRule.onNodeWithTag(SETTINGS_BUTTON).performClick()
        assertTrue(navController.currentBackStackEntry?.destination?.hasRoute<AppSettings>() ?: false)

    }

    @Test
    fun onClickNotesButton_navigatesToNotesScreen() {
        waitUntilComposableShowUp(CALENDAR_CONTENT)
        //openSideMenu()
        composeTestRule.onNodeWithTag(NOTES_BUTTON).performClick()
        assertTrue(navController.currentBackStackEntry?.destination?.hasRoute<Notes>() ?: false)

    }

    @Test
    fun onClickMeasurementsButton_navigatesToMeasurementsScreen() {
        waitUntilComposableShowUp(CALENDAR_CONTENT)
        //openSideMenu()
        composeTestRule.onNodeWithTag(MEASUREMENTS_BUTTON).performClick()
        assertTrue(navController.currentBackStackEntry?.destination?.hasRoute<Measurements>() ?: false)
    }


    @Test
    fun onClickOnADay_navigatesToReportsScreen() {
        waitUntilComposableShowUp(CALENDAR_CONTENT)
        navigateToReport()
        assertTrue(navController.currentBackStackEntry?.destination?.hasRoute<Report>() ?: false)

    }

    @Test
    fun onClickWorkoutButtonOnReports_navigatesToWorkoutScreen() {
        waitUntilComposableShowUp(CALENDAR_CONTENT)
        navigateToReport()
        composeTestRule.onNodeWithTag(WORKOUT_BUTTON_REPORT)
            .performClick()
        assertTrue(navController.currentBackStackEntry?.destination?.hasRoute<Workout>() ?: false)

    }

    @Test
    fun onClickBodyMeasurementButtonOnReports_navigatesBodyMeasurementScreen() {
        waitUntilComposableShowUp(CALENDAR_CONTENT)
        navigateToReport()
        composeTestRule.onNodeWithTag(NUTRITION_TAB).performClick()
        composeTestRule.onNodeWithTag(BODY_MEASUREMENT_BUTTON).performClick()
        assertTrue(navController.currentBackStackEntry?.destination?.hasRoute<Measurement>() ?: false)

    }


    private fun navigateToReport() {
        composeTestRule.onNodeWithText("20").performClick()
    }


    private fun waitUntilComposableShowUp(
        tag: String
    ) {
        composeTestRule.waitUntil {
            composeTestRule
                .onAllNodesWithTag(tag)
                .fetchSemanticsNodes().size == 1
        }
    }
}

