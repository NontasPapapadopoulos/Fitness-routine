package com.example.fitness_routine.domain.repository

import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface DailyRoutineRepository {

    fun getDailyReports(userId: String): Flow<List<DailyReportDomainEntity>>

    fun getDailyReport(userId: String, date: Date): Flow<DailyReportDomainEntity>

    suspend fun update(report: DailyReportDomainEntity)

    suspend fun delete(report: DailyReportDomainEntity)

    suspend fun put(report: DailyReportDomainEntity)

    suspend fun initDailyReport(userId: String, date: Date)

}