package com.example.fitness_routine.data.datasource

import com.example.fitness_routine.data.cache.dao.DailyReportDao
import com.example.fitness_routine.data.entity.DailyReportDataEntity
import com.example.fitness_routine.data.toTimeStamp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import java.util.Date
import javax.inject.Inject

interface DailyRoutineDataSource {
    fun getDailyReports(): Flow<List<DailyReportDataEntity>>

    fun getDailyReport(date: Date): Flow<DailyReportDataEntity>

    suspend fun update(report: DailyReportDataEntity)

    suspend fun delete(report: DailyReportDataEntity)

    suspend fun put(report: DailyReportDataEntity)

}



class DailyRoutineDataSourceImpl @Inject constructor(
    private val dao: DailyReportDao
): DailyRoutineDataSource {

    override fun getDailyReports(): Flow<List<DailyReportDataEntity>> {
        return dao.getReports()
    }

    override fun getDailyReport(date: Date): Flow<DailyReportDataEntity> {
        return dao.getReport(date.toTimeStamp()).filterNotNull()
    }

    override suspend fun update(report: DailyReportDataEntity) {
        dao.update(report)
    }

    override suspend fun delete(report: DailyReportDataEntity) {
        dao.delete(report)
    }

    override suspend fun put(report: DailyReportDataEntity) {
        dao.put(report)
    }


}