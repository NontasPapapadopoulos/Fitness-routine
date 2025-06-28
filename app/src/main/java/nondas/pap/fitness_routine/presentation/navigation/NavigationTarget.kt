package nondas.pap.fitness_routine.presentation.navigation

import kotlinx.serialization.Serializable
import nondas.pap.fitness_routine.domain.entity.enums.Muscle


interface Screen

@Serializable
object Splash: Screen

@Serializable
object Calendar: Screen

@Serializable
data class Report(val date: Long)

@Serializable
object Cheat

@Serializable
object AppSettings

@Serializable
object Measurements

@Serializable
object Notes

@Serializable
object Analytics

@Serializable
data class Workout(val date: Long)

@Serializable
data class Exercise(val muscle: Muscle?)

@Serializable
data class Measurement(val date: Long)

@Serializable
object GymSessions

enum class NavigationTarget {
    Calendar,
    Cheat,
    Gym,
    Workout,
    Exercise,
    Settings,
    Measurements,
    Notes,
    Analytics
}




enum class NavigationArgument (val param: String) {
    Date("date"),
    Muscle("muscle")
}





