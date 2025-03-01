package nondas.pap.fitness_routine.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import nondas.pap.fitness_routine.domain.entity.enums.Muscle


@Entity(
    tableName = "Break",
)
data class BreakDataEntity(
    @PrimaryKey
    val id: Long = 0L,
    val date: Long,
    val index: String,
    val muscle: Muscle,
    val exercise: String,
    val time: Long
)
