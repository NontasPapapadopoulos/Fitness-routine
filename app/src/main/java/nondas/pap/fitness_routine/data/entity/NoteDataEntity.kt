package nondas.pap.fitness_routine.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Note",
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
data class NoteDataEntity(
    @PrimaryKey val id: String,
    val note: String,
    val reportDate: Long
)