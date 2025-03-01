package nondas.pap.fitness_routine.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Cardio"
)
data class CardioDataEntity(
    @PrimaryKey
    val id: String,
    val date: Long,
    val type: String,
    val minutes: String
)
