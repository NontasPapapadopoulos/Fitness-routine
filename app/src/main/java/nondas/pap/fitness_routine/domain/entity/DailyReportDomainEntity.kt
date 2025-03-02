package nondas.pap.fitness_routine.domain.entity

import nondas.pap.fitness_routine.domain.entity.enums.Cardio
import nondas.pap.fitness_routine.domain.entity.enums.Muscle
import java.util.Date

data class DailyReportDomainEntity(
    val date: Date,
    val performedWorkout: Boolean,
    val hadCreatine: Boolean,
    val hadCheatMeal: Boolean,
    val proteinGrams: String,
    val sleepQuality: String, // from 1 to 5
    val litersOfWater: String,
    val musclesTrained: List<Muscle>,
)

