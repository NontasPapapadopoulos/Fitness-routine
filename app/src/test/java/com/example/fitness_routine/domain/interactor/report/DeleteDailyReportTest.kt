package com.example.fitness_routine.domain.interactor.report

import com.example.fitness_routine.domain.DummyEntities
import com.example.fitness_routine.domain.dailyReport
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
class DeleteDailyReportTest {

    private lateinit var deleteDailyReport: DeleteDailyReport

    @Mock
    private lateinit var dailyReportRepository: DailyRoutineRepository

    private var dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        deleteDailyReport = DeleteDailyReport(dailyReportRepository, dispatcher)
    }


    @Test
    fun execute_DeleteDailyReport() = runTest {
        whenever(dailyReportRepository.delete(any())).thenReturn(Unit)

        val result = deleteDailyReport.execute(DeleteDailyReport.Params(DummyEntities.dailyReport))

        assertEquals(
            result,
            Result.success(Unit)
        )
    }
}