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
class AddCardioTest {

    private lateinit var addCardio: AddCardio

    @Mock
    private lateinit var cardioRepository: CardioRepository

    private val dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        addCardio = AddCardio(cardioRepository, dispatcher)
    }


    @Test
    fun execute_addCardio() = runTest {
        whenever(cardioRepository.put(any())).thenReturn(Unit)

        val result = addCardio.execute(AddCardio.Params(date = Date(), type = "type", minutes = "22"))

        assertEquals(
            result,
            Result.success(Unit)
        )
    }
}