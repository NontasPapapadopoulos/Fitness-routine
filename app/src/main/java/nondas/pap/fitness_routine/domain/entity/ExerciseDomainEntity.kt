package nondas.pap.fitness_routine.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import nondas.pap.fitness_routine.domain.entity.enums.Muscle


data class ExerciseDomainEntity(
    val name: String,
    val muscle: Muscle,
)
