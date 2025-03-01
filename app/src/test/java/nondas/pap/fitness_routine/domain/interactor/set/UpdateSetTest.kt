package com.example.fitness_routine.domain.interactor.set

import com.example.fitness_routine.DummyEntities
import com.example.fitness_routine.domain.repository.SetRepository
import com.example.fitness_routine.domain.toTimeStamp
import com.example.fitness_routine.set
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
import java.util.Date
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class UpdateSetTest {

    private lateinit var updateSet: UpdateSet

    @Mock
    private lateinit var setRepository: SetRepository

    private var dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        updateSet = UpdateSet(setRepository, dispatcher)
    }


    @Test
    fun execute_updateSet() = runTest {
        whenever(setRepository.update(any())).thenReturn(Unit)

        val result = updateSet.execute(params = UpdateSet.Params(set))

        assertEquals(
            result,
            Result.success(Unit)
        )
    }


    companion object {
        val set = DummyEntities.set.copy(date = Date().toTimeStamp())
    }
}