package nondas.pap.fitness_routine.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutWithSetsDataEntity(
    @Embedded val workout: WorkoutDataEntity,
    @Relation(
        parentColumn = "date",
        entityColumn = "workoutDate"
    )
    val sets: List<SetDataEntity>
)