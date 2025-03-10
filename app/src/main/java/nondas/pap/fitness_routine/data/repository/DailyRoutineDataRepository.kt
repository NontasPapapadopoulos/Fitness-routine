package nondas.pap.fitness_routine.data.repository

import nondas.pap.fitness_routine.data.datasource.DailyRoutineDataSource
import nondas.pap.fitness_routine.data.mapper.toData
import nondas.pap.fitness_routine.data.mapper.toDomain
import nondas.pap.fitness_routine.domain.entity.DailyReportDomainEntity
import nondas.pap.fitness_routine.domain.repository.DailyRoutineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

class DailyRoutineDataRepository(
    private val dailyRoutineDataSource: DailyRoutineDataSource
): DailyRoutineRepository {

    override fun getDailyReports(): Flow<List<DailyReportDomainEntity>> {
        return dailyRoutineDataSource.getDailyReports().map { report -> report.map { it.toDomain() } }
    }

    override fun getDailyReport(date: Long): Flow<DailyReportDomainEntity> {
        return dailyRoutineDataSource.getDailyReport(date).map { it.toDomain() }
    }

    override suspend fun update(report: DailyReportDomainEntity) {
        dailyRoutineDataSource.update(report.toData())
    }

    override suspend fun delete(report: DailyReportDomainEntity) {
        dailyRoutineDataSource.delete(report.toData())
    }

    override suspend fun put(report: DailyReportDomainEntity) {
        dailyRoutineDataSource.put(report.toData())
    }

    override suspend fun initDailyReport(date: Long) {
        dailyRoutineDataSource.initDailyReport(date)
    }

}