package com.example.fitness_routine.presentation.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.fitness_routine.presentation.ui.screen.calendar.CalendarScreen
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class RootNavGraphKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var navController: TestNavHostController


    @Before
    fun setUpNavHost() {
//        composeTestRule.setContent {
//            navController = TestNavHostController(LocalContext.current)
//
//            navController.navigatorProvider.addNavigator(
//                ComposeNavigator()
//            )
//
////            CalendarScreen(navigateToDailyReport = {}, navigateToScreen = {})
//            RootNavGraph(navController = navController)
//        }
    }

    @Test
    fun routeNavHost_verifySplashStartDestination() {

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)

            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )

            RootNavGraph(navController = navController)
        }

        composeTestRule
            .onNodeWithContentDescription(Screen.Splash.name)
            .assertIsDisplayed()
    }

    @Test
    fun routeNavHost_clickGym_navigatesToGymSessions() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)

            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )

            CalendarScreen(navigateToDailyReport = {}, navigateToScreen = {})
        }

        composeTestRule
            .onNodeWithTag(Screen.Gym.name)
            .performClick()

        composeTestRule
            .onNodeWithContentDescription(Screen.Gym.name)
            .assertIsDisplayed()

    }




}