package nondas.pap.fitness_routine.domain.entity

import nondas.pap.fitness_routine.domain.entity.enums.Muscle


data class ExerciseDomainEntity(
    val name: String,
    val muscle: Muscle,
)
