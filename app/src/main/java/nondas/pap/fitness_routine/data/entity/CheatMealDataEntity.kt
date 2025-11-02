package nondas.pap.fitness_routine.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "CheatMeal",
    foreignKeys = [
        ForeignKey(
            entity = DailyReportDataEntity::class,
            parentColumns = ["date"],
            childColumns = ["reportDate"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("reportDate")]
)
data class CheatMealDataEntity(
    @PrimaryKey val id: String,
    val meal: String,
    val reportDate: Long
)