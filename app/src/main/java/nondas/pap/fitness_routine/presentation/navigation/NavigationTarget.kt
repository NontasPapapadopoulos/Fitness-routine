package nondas.pap.fitness_routine.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import nondas.pap.fitness_routine.domain.entity.enums.Muscle



@Serializable
object Splash: NavKey

@Serializable
object CalendarScreen: NavKey

@Serializable
data class Report(val date: Long): NavKey

@Serializable
object Cheat: NavKey

@Serializable
object AppSettings: NavKey

@Serializable
object Measurements: NavKey

@Serializable
object Notes: NavKey

@Serializable
object Analytics: NavKey

@Serializable
data class Workout(val date: Long): NavKey

@Serializable
data class Exercise(val muscle: Muscle?): NavKey

@Serializable
data class Measurement(val date: Long): NavKey

@Serializable
object GymSessions: NavKey





