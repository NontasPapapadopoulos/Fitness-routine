package com.example.fitness_routine.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.fitness_routine.domain.entity.enums.Muscle


@Entity(
    tableName = "Set",
//    foreignKeys = [
//        ForeignKey(
//            entity = WorkoutDataEntity::class,
//            parentColumns = ["date"],
//            childColumns = ["workoutDate"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ],
//    indices = [Index(value = ["workoutDate"])]
)
data class SetDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val workoutDate: Long,
    val muscle: Muscle,
    val exercise: String,
    val weight: Int,
    val repeats: Int,
)
