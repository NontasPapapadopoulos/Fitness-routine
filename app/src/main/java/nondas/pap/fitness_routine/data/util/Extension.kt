package nondas.pap.fitness_routine.data.util

import java.util.Date


fun Long.toDate(): Date = this.let { Date(it) }

fun Date.toTimeStamp(): Long = this.time



fun String.toList(): List<String> = this.split(",").map { it.trim() }

fun List<String>.convertToString(): String = this.joinToString(separator = ",")





