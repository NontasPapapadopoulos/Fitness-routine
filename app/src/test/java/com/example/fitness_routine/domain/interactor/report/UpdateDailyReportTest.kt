package com.example.fitness_routine.domain.interactor.report

import com.example.fitness_routine.DummyEntities
import com.example.fitness_routine.dailyReport
import com.example.fitness_routine.domain.repository.DailyRoutineRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class UpdateDailyReportTest {

    private lateinit var updateDailyReport: UpdateDailyReport

    @Mock
    private lateinit var dailyReportRepository: DailyRoutineRepository

    private var dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        updateDailyReport = UpdateDailyReport(dailyReportRepository, dispatcher)
    }


    @Test
    fun execute_initDailyReport() = runTest {
        whenever(dailyReportRepository.put(any())).thenReturn(Unit)

        val result = updateDailyReport.execute(UpdateDailyReport.Params(DummyEntities.dailyReport))

        assertEquals(
            result,
            Result.success(Unit)
        )
    }
}