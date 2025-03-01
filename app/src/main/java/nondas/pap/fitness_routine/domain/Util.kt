package nondas.pap.fitness_routine.domain

import nondas.pap.fitness_routine.domain.entity.enums.Muscle
import java.util.Date


fun List<String>.toMuscles(): List<Muscle> = if (this.isEmpty()) listOf() else this.map { Muscle.valueOf(it) }

//fun List<Muscle>.toMuscleList

fun Date.toTimeStamp(): Long = this.time
