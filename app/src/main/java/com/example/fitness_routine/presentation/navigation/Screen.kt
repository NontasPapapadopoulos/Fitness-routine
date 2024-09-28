package com.example.fitness_routine.presentation.navigation

enum class Screen {
    Splash,
    Calendar,
    Report
}


fun Screen.params(vararg  params: Any?): String {
    val routeParams = params.map { it.toString() }
    return this.name + "/" + routeParams.joinToString(separator = "/")
}