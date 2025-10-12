package nondas.pap.fitness_routine.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import nondas.pap.fitness_routine.presentation.ui.screen.bodymeasurement.BodyMeasurementScreen
import nondas.pap.fitness_routine.presentation.ui.screen.calendar.CalendarScreen
import nondas.pap.fitness_routine.presentation.ui.screen.cheat.CheatMealsScreen
import nondas.pap.fitness_routine.presentation.ui.screen.exercise.ExerciseScreen
import nondas.pap.fitness_routine.presentation.ui.screen.gym.GymSessionsScreen
import nondas.pap.fitness_routine.presentation.ui.screen.measurements.MeasurementsScreen
import nondas.pap.fitness_routine.presentation.ui.screen.report.ReportScreen
import nondas.pap.fitness_routine.presentation.ui.screen.settings.SettingsScreen
import nondas.pap.fitness_routine.presentation.ui.screen.workout.WorkoutScreen
import nondas.pap.fitness_routine.presentation.ui.screen.notes.NotesScreen
import nondas.pap.fitness_routine.presentation.ui.screen.splash.SplashScreen
import nondas.pap.fitness_routine.presentation.util.getCurrentDate


@Composable
fun Navigation() {

    val backStack = rememberNavBackStack(Splash)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            // Add the default decorators for managing scenes and saving state
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            // Then add the view model store decorator
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = { route ->
            when (route) {
                is Splash ->
                    NavEntry(key = route) {
                        SplashScreen(
                            navigateToCalendarScreen = {
                                backStack.add(CalendarScreen)
                            }
                        )
                    }


                is CalendarScreen ->
                    NavEntry(key = route) {
                        CalendarScreen(
                            navigateToScreen = { screen ->
                                backStack.pushIfNotTop(screen)
                            },
                            navigateToDailyReport = { id ->
                                backStack.add(Report(id))
                            },
                        )
                    }


                is Workout ->
                    NavEntry(key = route) {
                        WorkoutScreen(
                            navigateBack = {
                                backStack.removeLastOrNull()
                            },
                            onNavigateToScreen = { screen ->
                                backStack.pushIfNotTop(screen)
                            },
                            onNavigateToExercises = {
                                backStack.add(Exercise(it))
                            },
                            date = route.date
                        )
                    }


                is Cheat ->
                    NavEntry(key = route) {
                        CheatMealsScreen(
                            navigateToScreen = { screen ->
                                backStack.pushIfNotTop(screen)
                            },
                            navigateBack = {
                                backStack.removeLastOrNull()
                            }
                        )
                    }

                is GymSessions ->
                    NavEntry(key = route) {
                        GymSessionsScreen(
                            navigateBack = {
                                backStack.removeLastOrNull()
                            },
                            navigateToScreen = { screen ->
                                backStack.pushIfNotTop(screen)
                            },
                            navigateToWorkoutScreen = {
                                backStack.add(Workout(getCurrentDate()))
                            },
                        )
                    }


                is Report ->
                    NavEntry(key = route) {
                        ReportScreen(
                            navigateBack = {
                                backStack.removeLastOrNull()
                            },
                            navigateToWorkout = {
                                backStack.add(Workout(it))
                            },
                            navigateToBodyMeasurement = {
                                backStack.add(Measurement(it))
                            },
                            date = route.date
                        )
                    }


                is AppSettings ->
                    NavEntry(key = route) {
                        SettingsScreen(
                            navigateBack = {
                                backStack.removeLastOrNull()
                            },
                        )
                    }


                is Measurements ->
                    NavEntry(key = route) {
                        MeasurementsScreen(
                            navigateBack = {
                                backStack.removeLastOrNull()
                            },
                            navigateToBodyMeasurement = {
                                backStack.add(Measurement(it))
                            }
                        )
                    }


                is Notes ->
                    NavEntry(key = route) {
                        NotesScreen(
                            navigateBack = {
                                backStack.removeLastOrNull()
                            }
                        )
                    }


                is Exercise ->
                    NavEntry(key = route) {
                        ExerciseScreen(
                            navigateBack = {
                                println("BACKSTACK")
                                backStack.forEach { route ->
                                    println(route)
                                }
                                backStack.removeLastOrNull()
                            },
                            muscle = route.muscle?.name
                        )
                    }


                is Measurement ->
                    NavEntry(key = route) {
                        BodyMeasurementScreen(
                            navigateBack = {
                                backStack.removeLastOrNull()
                            },
                            date = route.date
                        )
                    }

                else -> error("Unknown route: $route")
            }
        }
    )

}


fun <NavKey> MutableList<NavKey>.pushIfNotTop(item: NavKey) {
    if (lastOrNull() != item) {
        add(item)
    }
}

