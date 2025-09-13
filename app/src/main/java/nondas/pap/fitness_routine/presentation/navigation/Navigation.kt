package nondas.pap.fitness_routine.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
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


@Composable
fun Navigation() {

    val backStack = rememberNavBackStack(Splash)

    NavDisplay(
        backStack = backStack
    ) { route ->
        NavEntry(route) {
            when (route) {
                is Splash ->
                    SplashScreen(
                        navigateToCalendarScreen = {
                            backStack.add(CalendarScreen)
                        }
                    )

                is CalendarScreen ->
                    CalendarScreen(
                        navigateToScreen = { screen ->
                            backStack.add(screen)
                        },
                        navigateToDailyReport = { date ->
                            backStack.add(Report(date))
                        },
                    )


                is Report ->
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
                    )


                is Cheat ->
                    CheatMealsScreen(
                        navigateToScreen = { screen ->
                            backStack.add(screen)
                        },
                        navigateBack = {
                            backStack.removeLastOrNull()
                        }
                    )


                is AppSettings ->
                    SettingsScreen(
                        navigateBack = {
                            backStack.removeLastOrNull()
                        },
                    )


                is Measurements ->
                    MeasurementsScreen(
                        navigateBack = {
                            backStack.removeLastOrNull()
                            backStack.add(CalendarScreen)
                        },
                        navigateToBodyMeasurement = {
                            backStack.add(Measurement(it))
                        }
                    )


                is Notes ->
                    NotesScreen(
                        navigateBack = {
                            backStack.removeLastOrNull()
                        }
                    )


                is Workout ->
                    WorkoutScreen(
                        navigateBack = {
                            backStack.removeLastOrNull()
                        },
                        onNavigateToScreen = { screen ->
                            backStack.removeLastOrNull()
                            backStack.add(screen)
                        },
                        onNavigateToExercises = {
                            backStack.add(Exercise(null))
                        }
                    )


                is Exercise ->
                    ExerciseScreen(
                        navigateBack = {
                            backStack.removeLastOrNull()
                        }
                    )


                is Measurement ->
                    BodyMeasurementScreen(
                        navigateBack = {
                            backStack.removeLastOrNull()
                        }
                    )


                is GymSessions ->
                    GymSessionsScreen(
                        navigateBack = {
                            backStack.removeLastOrNull()
                        },
                        navigateToScreen = { screen ->
                            backStack.add(screen)
                        },
                        navigateToWorkoutScreen = {},
                    )

            }
        }
    }
}
