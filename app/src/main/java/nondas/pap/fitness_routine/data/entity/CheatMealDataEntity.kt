package nondas.pap.fitness_routine.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "CheatMeal"
)
data class CheatMealDataEntity(
    @PrimaryKey
    val id: String,
    val date: Long,
    val meal: String,
)
