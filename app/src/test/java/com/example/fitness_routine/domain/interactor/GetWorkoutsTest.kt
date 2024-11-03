package com.example.fitness_routine.domain.interactor

import com.example.fitness_routine.DummyEntities
import com.example.fitness_routine.dailyReport
import com.example.fitness_routine.domain.repository.DailyRoutineRepository
import com.example.fitness_routine.domain.toTimeStamp
import com.example.fitness_routine.workout
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
class GetWorkoutsTest {

    private lateinit var getWorkouts: GetWorkouts

    @Mock
    private lateinit var dailyRoutineRepository: DailyRoutineRepository

    private val dispatcher = UnconfinedTestDispatcher()



    @Before
    fun setUp() {
        getWorkouts = GetWorkouts(dailyRoutineRepository, dispatcher)
    }


    @Test
    fun execute_getWorkouts() = runTest {
        whenever(dailyRoutineRepository.getDailyReports()).thenReturn(flowOf(listOf(report)))

        val result = getWorkouts.execute(Unit).first()

        assertEquals(
            Result.success(listOf(workout.copy(date = report.date.toTimeStamp()))),
            result
        )

    }


    companion object {
        val report = DummyEntities.dailyReport.copy(performedWorkout = true)
        val workout = DummyEntities.workout
    }
}