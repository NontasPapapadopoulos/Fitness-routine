package com.example.fitness_routine.presentation.navigation

enum class NavigationArgument (val param: String) {
    Date("date"),
}



fun NavigationArgument.addBrackets(): String = "{$param}"
