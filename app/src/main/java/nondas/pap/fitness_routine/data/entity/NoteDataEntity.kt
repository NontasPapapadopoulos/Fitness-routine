package nondas.pap.fitness_routine.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Note"
)
data class NoteDataEntity(
    @PrimaryKey
    val id: String,
    val date: Long,
    val note: String
)
