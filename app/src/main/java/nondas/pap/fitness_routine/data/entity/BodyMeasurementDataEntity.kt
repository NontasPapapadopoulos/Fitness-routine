package nondas.pap.fitness_routine.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "BodyMeasurement",
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
data class BodyMeasurementDataEntity(
    @PrimaryKey val id: String,
    val weight: String,
    val fat: String,
    val muscleMass: String,
    val bmi: String,
    val tbw: String,
    val bmr: String,
    val visceralFat: String,
    val metabolicAge: String,
    val reportDate: Long
)