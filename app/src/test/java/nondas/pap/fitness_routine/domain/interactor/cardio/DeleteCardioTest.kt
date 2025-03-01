package com.example.fitness_routine.domain.interactor.cardio

import com.example.fitness_routine.DummyEntities
import com.example.fitness_routine.cardio
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
class DeleteCardioTest {

    private lateinit var deleteCardio: DeleteCardio

    @Mock
    private lateinit var cardioRepository: CardioRepository

    private val dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        deleteCardio = DeleteCardio(cardioRepository, dispatcher)
    }


    @Test
    fun execute_deleteCardio() = runTest {
        whenever(cardioRepository.delete(any())).thenReturn(Unit)

        val result = deleteCardio.execute(DeleteCardio.Params(cardio = DummyEntities.cardio))

        assertEquals(
            result,
            Result.success(Unit)
        )
    }
}