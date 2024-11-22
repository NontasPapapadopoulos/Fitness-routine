package com.example.fitness_routine.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.fitness_routine.presentation.ui.screen.splash.SplashScreen
import com.example.fitness_routine.presentation.ui.screen.calendar.CalendarScreen
import com.example.fitness_routine.presentation.ui.screen.cheat.CheatMealsScreen
import com.example.fitness_routine.presentation.ui.screen.exercise.ExerciseScreen
import com.example.fitness_routine.presentation.ui.screen.gym.GymSessionsScreen
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreen
import com.example.fitness_routine.presentation.ui.screen.settings.SettingsScreen
import com.example.fitness_routine.presentation.ui.screen.workout.WorkoutScreen
import com.example.fitness_routine.presentation.util.getCurrentDate
import kotlinx.coroutines.delay


const val ROOT_GRAPH_ROUTE = "root"

@Composable
fun RootNavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        route = ROOT_GRAPH_ROUTE,
        startDestination = Screen.Splash.name
    ) {

        composable(
            route = Screen.Splash.name
        ) {

            DisplaySplashScreen(
                navController = navController,
                milliseconds = 50L,
                route = Screen.Calendar.name
            )

            SplashScreen()
        }

        composable(
            route = Screen.Calendar.name,
        ) {

            CalendarScreen(
                navigateToDailyReport = { date -> navController.navigate(Screen.Report.params(date)) },
                navigateToScreen = {

                    when (it) {
                        Screen.Gym,
                        Screen.Cheat -> { navController.navigate(it.name) { launchSingleTop = true } }
                        Screen.Exercise -> { navController.navigate(Screen.Exercise.params(null)) { launchSingleTop = true } }
                        Screen.Settings -> { navController.navigate(Screen.Settings.name) { launchSingleTop = true } }
                        Screen.Workout -> { navController.navigate(Screen.Workout.params(getCurrentDate())) { launchSingleTop = true } }
                        else -> {}
                    }
                },
            )
        }


        composable(
            route = ReportsRoute,
            arguments = listOf(
                navArgument(NavigationArgument.Date.param) {
                    type = NavType.LongType
                }
            )
        ) {

            ReportScreen(
                navigateBack = { navController.popBackStack() },
                navigateToWorkout = { date -> navController.navigate(Screen.Workout.params(date)) }
            )
        }

        composable(
            route = CheatMealsRoute
        ) {
            CheatMealsScreen(
                navigateToScreen = {
                    when (it) {
                        Screen.Calendar,
                        Screen.Gym -> { navController.navigate(it.name) { launchSingleTop = true } }
                        Screen.Workout -> { navController.navigate(Screen.Workout.params(getCurrentDate())) { launchSingleTop = true } }
                        else -> {}
                    }
                }
            )
        }

        composable(
            route = GymRoute
        ) {
            GymSessionsScreen(
                navigateToScreen = {
                    when (it) {
                        Screen.Calendar,
                        Screen.Cheat -> { navController.navigate(it.name) { launchSingleTop = true } }
                        Screen.Workout -> { navController.navigate(Screen.Workout.params(getCurrentDate())) { launchSingleTop = true } }
                        else -> {}
                    }

                },
                navigateToWorkoutScreen = { date -> navController.navigate(Screen.Workout.params(date)) }
            )
        }


        composable(
            route = WorkoutRoute,
            arguments = listOf(
                navArgument(NavigationArgument.Date.param) {
                    type = NavType.LongType
                }
            )
        ) {
            WorkoutScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateToExercises = { muscle -> navController.navigate(Screen.Exercise.params(muscle)) },
                onNavigateToScreen = {
                    when (it) {
                        Screen.Calendar,
                        Screen.Gym,
                        Screen.Cheat -> {
                            navController.navigate(it.name)
                        }
                        else -> {}
                    }
                }

            )
        }


        composable(
            route = ExerciseRoute,
            arguments = listOf(
                navArgument(NavigationArgument.Muscle.name) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            ExerciseScreen(navigateBack = { navController.popBackStack() })
        }


        composable(
            route = SettingsRoute
        ) {
            SettingsScreen(navigateBack = { navController.popBackStack() })
        }

    }

}



@Composable
fun DisplaySplashScreen(
    navController: NavController,
    milliseconds: Long,
    route: String
) {
    LaunchedEffect(Unit) {
        delay(milliseconds)
        navController.navigate(route = route)
    }

}


fun NavHostController.navigateSingleTopToxxx(route: String) =
    this.navigate(route) { launchSingleTop = true }


fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }