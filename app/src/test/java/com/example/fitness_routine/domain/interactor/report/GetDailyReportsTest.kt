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
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetDailyReportsTest {

    private lateinit var getDailyReports: GetDailyReports

    @Mock
    private lateinit var dailyReportRepository: DailyRoutineRepository

    private var dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        getDailyReports = GetDailyReports(dailyReportRepository, dispatcher)
    }


    @Test
    fun execute_getDailyReport() = runTest {
        whenever(dailyReportRepository.getDailyReports()).thenReturn(flowOf(listOf(report)))

        val result = getDailyReports.execute(Unit).first()

        assertEquals(
            result,
            Result.success(listOf(report))
        )
    }



    companion object {
        val report = DummyEntities.dailyReport
    }
}