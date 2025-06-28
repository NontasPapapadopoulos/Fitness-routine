package nondas.pap.fitness_routine.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import nondas.pap.fitness_routine.presentation.ui.screen.analytics.AnalyticsScreen
import nondas.pap.fitness_routine.presentation.ui.screen.bodymeasurement.BodyMeasurementScreen
import nondas.pap.fitness_routine.presentation.ui.screen.splash.SplashScreen
import nondas.pap.fitness_routine.presentation.ui.screen.calendar.CalendarScreen
import nondas.pap.fitness_routine.presentation.ui.screen.cheat.CheatMealsScreen
import nondas.pap.fitness_routine.presentation.ui.screen.exercise.ExerciseScreen
import nondas.pap.fitness_routine.presentation.ui.screen.gym.GymSessionsScreen
import nondas.pap.fitness_routine.presentation.ui.screen.measurements.MeasurementsScreen
import nondas.pap.fitness_routine.presentation.ui.screen.report.ReportScreen
import nondas.pap.fitness_routine.presentation.ui.screen.settings.SettingsScreen
import nondas.pap.fitness_routine.presentation.ui.screen.workout.WorkoutScreen
import nondas.pap.fitness_routine.presentation.util.getCurrentDate
import kotlinx.coroutines.delay
import nondas.pap.fitness_routine.presentation.ui.screen.notes.NotesScreen



@Composable
fun RootNavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Splash
    ) {

        composable<Splash> {
            DisplaySplashScreen(
                navController = navController,
                milliseconds = 1000L,
                route = Calendar
            )

            SplashScreen()
        }

        composable<Calendar> {
            CalendarScreen(
                navigateToDailyReport = { date -> navController.navigate(Report(date)) },
                navigateToScreen = { screen ->
                    when (screen) {
                        NavigationTarget.Gym,
                        NavigationTarget.Cheat -> { navController.navigate(Cheat) { launchSingleTop = true } }
                        NavigationTarget.Exercise -> { navController.navigate(Exercise(null)) { launchSingleTop = true } }
                        NavigationTarget.Settings -> { navController.navigate(AppSettings) { launchSingleTop = true } }
                        NavigationTarget.Workout -> { navController.navigate(Workout(date = getCurrentDate())) { launchSingleTop = true } }
                        NavigationTarget.Measurements -> { navController.navigate(Measurements) { launchSingleTop = true } }
                        NavigationTarget.Notes -> { navController.navigate(Notes)  { launchSingleTop = true } }
                        NavigationTarget.Analytics -> { navController.navigate(Analytics) { launchSingleTop = true } }
                        else -> {}
                    }
                },
            )
        }


        composable<Report> {
            ReportScreen(
                navigateBack = { navController.popBackStack() },
                navigateToWorkout = { date -> navController.navigate(Workout(date)) },
                navigateToBodyMeasurement = { date -> navController.navigate(Measurement(date)) }
            )
        }

        composable<Cheat> {
            CheatMealsScreen(
                navigateToScreen = {
                    when (it) {
                        NavigationTarget.Calendar,
                        NavigationTarget.Gym -> { navController.navigate(GymSessions) { launchSingleTop = true } }
                        NavigationTarget.Workout -> { navController.navigate(Workout(getCurrentDate())) { launchSingleTop = true } }
                        else -> {}
                    }
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable<GymSessions> {
            GymSessionsScreen(
                navigateToScreen = {
                    when (it) {
                        NavigationTarget.Calendar,
                        NavigationTarget.Cheat -> { navController.navigate(Cheat) { launchSingleTop = true } }
                        NavigationTarget.Workout -> { navController.navigate(Workout(getCurrentDate())) { launchSingleTop = true } }
                        else -> {}
                    }

                },
                navigateToWorkoutScreen = { date -> navController.navigate(Workout(date)) },
                navigateBack = { navController.popBackStack() }
            )
        }


        composable<Workout> {
            WorkoutScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateToExercises = { muscle -> navController.navigate(Exercise(muscle)) },
                onNavigateToScreen = {
                    when (it) {
                        NavigationTarget.Calendar -> { navController.navigate(Calendar) }
                        NavigationTarget.Gym -> { navController.navigate(GymSessions) }
                        NavigationTarget.Cheat -> { navController.navigate(Cheat) }
                        else -> {}
                    }
                }
            )
        }


        composable<Exercise> {
            ExerciseScreen(navigateBack = { navController.popBackStack() })
        }


        composable<AppSettings> {
            SettingsScreen(navigateBack = { navController.popBackStack() })
        }


        composable<Measurement> {
            BodyMeasurementScreen(
                navigateBack = { navController.popBackStack() }
            )
        }


        composable<Measurements> {
            MeasurementsScreen(
                navigateBack = { navController.popBackStack() },
                navigateToBodyMeasurement = { date -> navController.navigate(Measurement(date)) }
            )
        }


        composable<Notes> {
            NotesScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        composable<Analytics> {
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
    route: Any
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