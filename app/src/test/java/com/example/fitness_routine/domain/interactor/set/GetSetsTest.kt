package com.example.fitness_routine.domain.interactor.set

import com.example.fitness_routine.domain.DummyEntities
import com.example.fitness_routine.domain.repository.SetRepository
import com.example.fitness_routine.domain.set
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
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetSetsTest {

    private lateinit var getSets: GetSets

    @Mock
    private lateinit var setRepository: SetRepository

    private var dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        getSets = GetSets(setRepository, dispatcher)
    }


    @Test
    fun execute_getSets() = runTest {
        whenever(setRepository.getSets(any())).thenReturn(flowOf(listOf(set)))

        val result = getSets.execute(GetSets.Params(date = Date().toTimeStamp())).first()

        assertEquals(
            result,
            Result.success(listOf(set))
        )
    }




    companion object {
        val set = DummyEntities.set.copy(workoutDate = Date().toTimeStamp())
    }
}