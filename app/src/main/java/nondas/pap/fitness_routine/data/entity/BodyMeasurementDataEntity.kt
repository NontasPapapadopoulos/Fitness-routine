package nondas.pap.fitness_routine.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "BodyMeasurement"
)
data class BodyMeasurementDataEntity(
    @PrimaryKey
    val id: String,
    val date: Long,
    val weight: String,
    val fat: String,
    val muscleMass: String,
    val bmi: String,
    val tbw: String,
    val bmr: String,
    val visceralFat: String,
    val metabolicAge: String
)