package nondas.pap.fitness_routine.data.util

import nondas.pap.fitness_routine.domain.entity.enums.Muscle
import java.util.Date


fun Long.toDate(): Date = this.let { Date(it) }

fun Date.toTimeStamp(): Long = this.time



fun String.toList(): List<String> = this.split(",").map { it.trim() }
fun String.toMusclesList(): List<Muscle> = if (this.isNotEmpty()) this.split(",").map { it.trim() }
    .map { Muscle.valueOf(it) } else listOf()

fun List<String>.convertToString(): String = this.joinToString(separator = ",")
fun List<Muscle>.convertMuscleListToString(): String = if (this.isNotEmpty()) this.map { it.name }.joinToString(separator = ",")
else ""





