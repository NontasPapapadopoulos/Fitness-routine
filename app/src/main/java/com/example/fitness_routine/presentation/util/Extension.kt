package com.example.fitness_routine.presentation.util

import com.example.fitness_routine.domain.entity.enums.Muscle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun Long.toDate(): Date = this.let { Date(it) }

fun Date.toTimeStamp(): Long = this.time


fun Long.toFormattedDate(): String {
    val date = Date(this)
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(date)
}


fun Date.toFormattedDate(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(this)
}


fun String.toList(): List<String> = this.split(",").map { it.trim() }

fun List<String>.convertToString(): String = this.joinToString(separator = ",")



fun List<String>.toMuscles(): List<Muscle> = if (this.isEmpty()) listOf() else this.map { Muscle.valueOf(it) }
