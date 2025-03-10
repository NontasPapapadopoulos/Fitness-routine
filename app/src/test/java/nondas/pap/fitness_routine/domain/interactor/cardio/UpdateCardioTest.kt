package nondas.pap.fitness_routine.domain.interactor.cardio

import nondas.pap.fitness_routine.DummyEntities
import nondas.pap.fitness_routine.cardio
import nondas.pap.fitness_routine.domain.repository.CardioRepository
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
class UpdateCardioTest {

    private lateinit var updateCardio: UpdateCardio

    @Mock
    private lateinit var cardioRepository: CardioRepository

    private val dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        updateCardio = UpdateCardio(cardioRepository, dispatcher)
    }


    @Test
    fun execute_updateCardio() = runTest {
        whenever(cardioRepository.update(any())).thenReturn(Unit)

        val result = updateCardio.execute(UpdateCardio.Params(cardio = DummyEntities.cardio))

        assertEquals(
            result,
            Result.success(Unit)
        )
    }
}