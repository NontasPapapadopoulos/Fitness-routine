package nondas.pap.fitness_routine.domain.interactor.report

import nondas.pap.fitness_routine.domain.repository.DailyRoutineRepository
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
class InitDailyReportTest {

    private lateinit var initDailyReport: InitDailyReport

    @Mock
    private lateinit var dailyReportRepository: DailyRoutineRepository

    private var dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        initDailyReport = InitDailyReport(dailyReportRepository, dispatcher)
    }


    @Test
    fun execute_initDailyReport() = runTest {
        whenever(dailyReportRepository.initDailyReport(any())).thenReturn(Unit)


        val result = initDailyReport.execute(InitDailyReport.Params(date = 0))

        assertEquals(
            result,
            Result.success(Unit)
        )
    }
}