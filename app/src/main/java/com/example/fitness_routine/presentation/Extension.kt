package com.example.fitness_routine.presentation

import java.util.Date


fun Long.toDate(): Date = this.let { Date(it) }

fun Date.toTimeStamp(): Long = this.time


