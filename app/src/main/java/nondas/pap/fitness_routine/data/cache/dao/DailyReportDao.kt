package nondas.pap.fitness_routine.data.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import nondas.pap.fitness_routine.data.entity.DailyReportDataEntity
import kotlinx.coroutines.flow.Flow
import nondas.pap.fitness_routine.data.entity.BodyMeasurementDataEntity
import nondas.pap.fitness_routine.data.entity.CardioDataEntity
import nondas.pap.fitness_routine.data.entity.CheatMealDataEntity
import nondas.pap.fitness_routine.data.entity.DailyReport
import nondas.pap.fitness_routine.data.entity.ExerciseDataEntity
import nondas.pap.fitness_routine.data.entity.NoteDataEntity

// TODO: Study the relations of this dao
@Dao
interface DailyReportDao {

    // ✅ Get all reports as Flow
    @Query("SELECT * FROM DailyReport")
    fun getReports(): Flow<List<DailyReportDataEntity>>

    // ✅ Get single report (entity only, not relations)
    @Query("SELECT * FROM DailyReport WHERE date = :date")
    fun getReportFlow(date: Long): Flow<DailyReportDataEntity?>

    @Query("SELECT * FROM DailyReport WHERE date = :date")
    suspend fun getReport(date: Long): DailyReportDataEntity?

    // ✅ Basic CRUD operations for the base entity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: DailyReportDataEntity)

    @Update
    suspend fun updateReport(report: DailyReportDataEntity)

    @Delete
    suspend fun deleteReport(report: DailyReportDataEntity)

    // ✅ Full report with relationships (uses the POJO, not entity)
    @Transaction
    @Query("SELECT * FROM DailyReport WHERE date = :date")
    suspend fun getFullReport(date: Long): DailyReport?

    // ✅ Inserts for related entities
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: List<NoteDataEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCheatMeals(meals: List<CheatMealDataEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCardios(cardios: List<CardioDataEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBodyMeasurement(measurement: BodyMeasurementDataEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<ExerciseDataEntity>)

}
//@Dao
//interface DailyReportDao {
//
//    @Query("SELECT * FROM DailyReport")
//    fun getReports(): Flow<List<DailyReportDataEntity>>
//
//    @Query("SELECT * FROM DailyReport WHERE date = :date")
//    fun getReportFlow(date: Long): Flow<DailyReportDataEntity>
//
//    @Query("SELECT * FROM DailyReport WHERE date = :date")
//    suspend fun getReport(date: Long): DailyReportDataEntity?
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun put(dailyReportDataEntity: DailyReportDataEntity)
//
//    @Update
//    suspend fun update(report: DailyReportDataEntity)
//
//    @Delete
//    suspend fun delete(dailyReport: DailyReportDataEntity)
//
//}