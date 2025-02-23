package com.example.fitness_routine.domain.interactor.cardio

import com.example.fitness_routine.DummyEntities
import com.example.fitness_routine.cardio
import com.example.fitness_routine.domain.repository.CardioRepository
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
class GetCardioTest {

    private lateinit var getCardio: GetCardios

    @Mock
    private lateinit var cardioRepository: CardioRepository

    private val dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        getCardio = GetCardios(cardioRepository, dispatcher)
    }


    @Test
    fun execute_updateCardio() = runTest {
        val cardios = listOf(DummyEntities.cardio)
        whenever(cardioRepository.getCardios(any())).thenReturn(flowOf(cardios))

        val result = getCardio.execute(GetCardios.Params(date = 0))

        assertEquals(
            result.first(),
            Result.success(cardios)
        )
    }
}