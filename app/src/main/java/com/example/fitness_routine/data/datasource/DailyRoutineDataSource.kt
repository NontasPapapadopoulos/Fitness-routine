package com.example.fitness_routine.data.datasource

import com.example.fitness_routine.data.entity.DailyReportDataEntity
import com.example.fitness_routine.data.util.toTimeStamp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import java.util.Date
import javax.inject.Inject

interface DailyRoutineDataSource {
    fun getDailyReports(): Flow<List<DailyReportDataEntity>>

    fun getDailyReport(date: Long): Flow<DailyReportDataEntity>

    suspend fun update(report: DailyReportDataEntity)

    suspend fun delete(report: DailyReportDataEntity)

    suspend fun put(report: DailyReportDataEntity)

    suspend fun initDailyReport(date: Long)

}



//class DailyRoutineDataSourceImpl @Inject constructor(
//    private val dao: DailyReportDao
//): DailyRoutineDataSource {
//
//    override fun getDailyReports(): Flow<List<DailyReportDataEntity>> {
//        return dao.getReports()
//    }
//
//    override fun getDailyReport(date: Long): Flow<DailyReportDataEntity> {
//        return dao.getReportFlow(date).filterNotNull()
//    }
//
//    override suspend fun update(report: DailyReportDataEntity) {
//        dao.update(report)
//    }
//
//    override suspend fun delete(report: DailyReportDataEntity) {
//        dao.delete(report)
//    }
//
//    override suspend fun put(report: DailyReportDataEntity) {
//        dao.put(report)
//    }
//
//    override suspend fun initDailyReport(date: Long) {
//        val dailyReportExists = dao.getReport(date) != null
//
//        if (!dailyReportExists) {
//            val dailyReport = createDailyReport(date)
//            dao.put(dailyReport)
//        }
//
//    }
//
//    private fun createDailyReport(date: Long) = DailyReportDataEntity(
//        date = date,
//        performedWorkout = false,
//        hadCreatine = false,
//        hadCheatMeal = false,
//        proteinGrams = "",
//        sleepQuality = "",
//        litersOfWater = "",
//        musclesTrained = "",
//    )
//
//
//}