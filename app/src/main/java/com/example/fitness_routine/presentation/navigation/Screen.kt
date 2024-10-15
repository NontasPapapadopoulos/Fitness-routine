package com.example.fitness_routine.presentation.navigation

enum class Screen {
    Splash,
    Calendar,
    Report,
    Gym,
    Cheat,
    Workout,
    Exercise,
}


fun Screen.params(vararg  params: Any?): String {
    val routeParams = params.map { it.toString() }
    return this.name + "/" + routeParams.joinToString(separator = "/")
}



val ReportsRoute = "${Screen.Report.name}/${NavigationArgument.Date.addBrackets()}"
val GymRoute = "${Screen.Gym.name}/"
val CheatMealsRoute = "${Screen.Cheat.name}/"
val ExerciseRoute = "${Screen.Exercise.name}/"
val WorkoutRout = "${Screen.Workout.name}/${NavigationArgument.Date.addBrackets()}"
