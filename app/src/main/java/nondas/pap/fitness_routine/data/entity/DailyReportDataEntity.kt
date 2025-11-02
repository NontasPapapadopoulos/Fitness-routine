package nondas.pap.fitness_routine.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity(
    tableName = "DailyReport",
)
data class DailyReportDataEntity(
    @PrimaryKey
    val date: Long,
    val performedWorkout: Boolean,
    val hadCreatine: Boolean,
    val hadCheatMeal: Boolean,
    val proteinGrams: String,
    val sleepQuality: String, // from 1 to 5
    val litersOfWater: String,
    val musclesTrained: String,
)


data class DailyReport(
    @Embedded val report: DailyReportDataEntity,

    @Relation(parentColumn = "date", entityColumn = "reportDate")
    val notes: List<NoteDataEntity>,

    @Relation(parentColumn = "date", entityColumn = "reportDate")
    val cheatMeals: List<CheatMealDataEntity>,

    @Relation(parentColumn = "date", entityColumn = "reportDate")
    val cardios: List<CardioDataEntity>,

    @Relation(parentColumn = "date", entityColumn = "reportDate")
    val bodyMeasurement: BodyMeasurementDataEntity?,

    @Relation(
        entity = SetDataEntity::class,
        parentColumn = "date",
        entityColumn = "workoutDate"
    )
    val sets: List<SetDataEntity>
)



