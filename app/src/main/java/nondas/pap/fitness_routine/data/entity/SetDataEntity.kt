package nondas.pap.fitness_routine.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import nondas.pap.fitness_routine.domain.entity.enums.Muscle


@Entity(
    tableName = "Set",
    foreignKeys = [
        ForeignKey(
            entity = DailyReportDataEntity::class,
            parentColumns = ["date"],
            childColumns = ["workoutDate"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("workoutDate")]
)
data class SetDataEntity(
    @PrimaryKey
    val id: String,
    val workoutDate: Long,
    val muscle: Muscle,
    val exercise: String,
    val weight: String,
    val repeats: String,
)
