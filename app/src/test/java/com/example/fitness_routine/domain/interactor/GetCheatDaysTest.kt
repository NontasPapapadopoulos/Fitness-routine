package com.example.fitness_routine.domain.interactor

import com.example.fitness_routine.domain.DummyEntities
import com.example.fitness_routine.domain.cheatMeal
import com.example.fitness_routine.domain.dailyReport
import com.example.fitness_routine.domain.repository.DailyRoutineRepository
import com.example.fitness_routine.domain.toTimeStamp
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
class GetCheatDaysTest {

    private lateinit var getCheatDays: GetCheatDays

    @Mock
    private lateinit var dailyRoutineRepository: DailyRoutineRepository

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        getCheatDays = GetCheatDays(dailyRoutineRepository, dispatcher)
    }

    @Test
    fun execute_getCheatDays() = runTest {
        whenever(dailyRoutineRepository.getDailyReports()).thenReturn(flowOf(listOf(report)))

        val result = getCheatDays.execute(Unit).first()

        assertEquals(
            Result.success(listOf(cheatDay.copy(date = report.date))),
            result
        )

    }


    companion object {
        val report = DummyEntities.dailyReport.copy(hadCheatMeal = true)
        val cheatDay = DummyEntities.cheatMeal
    }
}