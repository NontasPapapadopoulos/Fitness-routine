package com.example.fitness_routine.presentation.navigation

import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.fitness_routine.presentation.ui.screen.analytics.AnalyticsScreen
import com.example.fitness_routine.presentation.ui.screen.bodymeasurement.BodyMeasurementScreen
import com.example.fitness_routine.presentation.ui.screen.splash.SplashScreen
import com.example.fitness_routine.presentation.ui.screen.calendar.CalendarScreen
import com.example.fitness_routine.presentation.ui.screen.cheat.CheatMealsScreen
import com.example.fitness_routine.presentation.ui.screen.exercise.ExerciseScreen
import com.example.fitness_routine.presentation.ui.screen.gym.GymSessionsScreen
import com.example.fitness_routine.presentation.ui.screen.login.LoginScreen
import com.example.fitness_routine.presentation.ui.screen.measurements.MeasurementsScreen
import com.example.fitness_routine.presentation.ui.screen.notes.NotesScreen
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

//            DisplaySplashScreen(
//                navController = navController,
//                milliseconds = 1000L,
//                route = Screen.Login.name
//            )

            SplashScreen(
                navigateToLoginScreen = { navController.navigate(Screen.Login.name) },
                navigateToCalendarScreen = { navController.navigate(Screen.Calendar.name) }
            )
        }


        composable(
            route = LoginRoute
        ) {
            LoginScreen(
                navigateToCalendarScreen = { navController.navigate(Screen.Calendar.name) }
            )
        }

        composable(
            route = Screen.Calendar.name,
        ) {

            CalendarScreen(
                navigateToDailyReport = { date -> navController.navigate(Screen.Report.params(date)) },
                navigateToLoginScreen = { navController.navigate(Screen.Login.name) },
                navigateToScreen = { screen ->
                    when (screen) {
                        Screen.Gym,
                        Screen.Cheat -> { navController.navigate(screen.name) { launchSingleTop = true } }
                        Screen.Exercise -> { navController.navigate(Screen.Exercise.params(null)) { launchSingleTop = true } }
                        Screen.Settings -> { navController.navigate(SettingsRoute) { launchSingleTop = true } }
                        Screen.Workout -> { navController.navigate(Screen.Workout.params(getCurrentDate())) { launchSingleTop = true } }
                        Screen.Measurements -> { navController.navigate(MeasurementsRoute) { launchSingleTop = true } }
                        Screen.Notes -> { navController.navigate(NotesRoute)  { launchSingleTop = true } }
                        Screen.Analytics -> { navController.navigate(AnalyticsRoute) { launchSingleTop = true } }
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
                navigateToWorkout = { date -> navController.navigate(Screen.Workout.params(date)) },
                navigateToBodyMeasurement = { date -> navController.navigate(Screen.Measurement.params(date)) }
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
                },
                navigateBack = { navController.popBackStack() }
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
                navigateToWorkoutScreen = { date -> navController.navigate(Screen.Workout.params(date)) },
                navigateBack = { navController.popBackStack() }
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


        composable(
            route = MeasurementRoute,
            arguments = listOf(
                navArgument(NavigationArgument.Date.param) {
                    type = NavType.LongType
                }
            )
        ) {
            BodyMeasurementScreen(
                navigateBack = { navController.popBackStack() }
            )
        }


        composable(
            route = MeasurementsRoute
        ) {
            MeasurementsScreen(
                navigateBack = { navController.popBackStack() },
                navigateToBodyMeasurement = { date -> navController.navigate(Screen.Measurement.params(date)) }
            )
        }


        composable(
            route = NotesRoute
        ) {
            NotesScreen(
                navigateBack = { navController.popBackStack() }
            )
        }//

        composable(
            route = AnalyticsRoute
        ) {
            AnalyticsScreen(
                navigateBack = { navController.popBackStack() }
            )
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