package nondas.pap.fitness_routine.domain.interactor.set

import nondas.pap.fitness_routine.DummyEntities
import nondas.pap.fitness_routine.domain.repository.SetRepository
import nondas.pap.fitness_routine.set
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
class DeleteSetTest {

    private lateinit var deleteSet: DeleteSet

    @Mock
    private lateinit var setRepository: SetRepository

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        deleteSet = DeleteSet(setRepository, dispatcher)
    }


    @Test
    fun execute_deleteSet() = runTest {
        whenever(setRepository.delete(any())).thenReturn(Unit)

        val result = deleteSet.execute(DeleteSet.Params(set))

        assertEquals(
            result,
            Result.success(Unit)
        )
    }


    companion object {
        val set = DummyEntities.set
    }
}