package com.example.fitness_routine.domain.interactor.report

import com.example.fitness_routine.DummyEntities
import com.example.fitness_routine.dailyReport
import com.example.fitness_routine.domain.repository.DailyRoutineRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetDailyReportTest {

    private lateinit var getDailyReport: GetDailyReport

    @Mock
    private lateinit var dailyReportRepository: DailyRoutineRepository

    private var dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        getDailyReport = GetDailyReport(dailyReportRepository, dispatcher)
    }


    @Test
    fun execute_getDailyReport() = runTest {
        whenever(dailyReportRepository.getDailyReport(any())).thenReturn(flowOf(report))

        val result = getDailyReport.execute(GetDailyReport.Params(Date())).first()

        assertEquals(
            result,
            Result.success(report)
        )
    }



    companion object {
        val report = DummyEntities.dailyReport
    }
}