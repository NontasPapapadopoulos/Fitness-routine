package com.example.fitness_routine.domain.interactor.cardio

import com.example.fitness_routine.domain.repository.CardioRepository
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
class InitCardioTest {

    private lateinit var initCardio: InitCardio

    @Mock
    private lateinit var cardioRepository: CardioRepository

    private val dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        initCardio = InitCardio(cardioRepository, dispatcher)
    }


    @Test
    fun execute_initCardio() = runTest {
        whenever(cardioRepository.init(any())).thenReturn(Unit)

        val result = initCardio.execute(InitCardio.Params(date = 0))

        assertEquals(
            result,
            Result.success(Unit)
        )
    }
}