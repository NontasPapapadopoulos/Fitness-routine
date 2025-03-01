package com.example.fitness_routine.presentation.navigation

enum class Screen {
    Splash,
    Calendar,
    Report,
    Gym,
    Cheat,
    Workout,
    Exercise,
    Settings,
    Measurement,
    Measurements,
    Notes,
    Analytics
}


fun Screen.params(vararg  params: Any?): String {
    val routeParams = params.map { it.toString() }
    return this.name + "/" + routeParams.joinToString(separator = "/")
}



val ReportsRoute = "${Screen.Report.name}/${NavigationArgument.Date.addBrackets()}"
val GymRoute = Screen.Gym.name
val CheatMealsRoute = Screen.Cheat.name
val ExerciseRoute = "${Screen.Exercise.name}/${NavigationArgument.Muscle.addBrackets()}"
val WorkoutRoute = "${Screen.Workout.name}/${NavigationArgument.Date.addBrackets()}"
val SettingsRoute = Screen.Settings.name
val MeasurementRoute = "${Screen.Measurement.name}/${NavigationArgument.Date.addBrackets()}"
val MeasurementsRoute = Screen.Measurements.name
val NotesRoute = Screen.Notes.name
val AnalyticsRoute = Screen.Analytics.name


enum class NavigationArgument (val param: String) {
    Date("date"),
    Muscle("muscle")
}


fun NavigationArgument.addBrackets(): String = "{$param}"

