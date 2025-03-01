package nondas.pap.fitness_routine.domain.interactor.set

import nondas.pap.fitness_routine.DummyEntities
import nondas.pap.fitness_routine.domain.interactor.set.GetSetsTest.Companion.set
import nondas.pap.fitness_routine.domain.repository.SetRepository
import nondas.pap.fitness_routine.domain.toTimeStamp
import nondas.pap.fitness_routine.set
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
import java.util.Date


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetAllSetsTest {

    private lateinit var getAllSets: GetAllSets

    @Mock
    private lateinit var  setRepository: SetRepository

    private var dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        getAllSets = GetAllSets(setRepository, dispatcher)
    }

    @Test
    fun execute_getAllSets() = runTest {
        whenever(setRepository.getAllSets()).thenReturn(flowOf(listOf(set)))

        val result = getAllSets.execute(Unit).first()

        assertEquals(
            result,
            Result.success(listOf(set))
        )
    }

    companion object {
        val set = DummyEntities.set.copy(date = Date().toTimeStamp())
    }
}