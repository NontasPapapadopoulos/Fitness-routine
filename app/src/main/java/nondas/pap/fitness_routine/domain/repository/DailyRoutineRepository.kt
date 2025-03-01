package nondas.pap.fitness_routine.domain.repository

import nondas.pap.fitness_routine.domain.entity.DailyReportDomainEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface DailyRoutineRepository {

    fun getDailyReports(): Flow<List<DailyReportDomainEntity>>

    fun getDailyReport(date: Long): Flow<DailyReportDomainEntity>

    suspend fun update(report: DailyReportDomainEntity)

    suspend fun delete(report: DailyReportDomainEntity)

    suspend fun put(report: DailyReportDomainEntity)

    suspend fun initDailyReport(date: Long)

}