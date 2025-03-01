package nondas.pap.fitness_routine.domain.interactor.set

import nondas.pap.fitness_routine.domain.entity.enums.Muscle
import nondas.pap.fitness_routine.domain.repository.SetRepository
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
class CreateNewSetTest {

    private lateinit var createNewSet: CreateNewSet

    @Mock
    private lateinit var setRepository: SetRepository

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        createNewSet = CreateNewSet(setRepository, dispatcher)
    }


    @Test
    fun execute_createNewSet() = runTest {
        whenever(setRepository.add(any())).thenReturn(Unit)

        val result = createNewSet.execute(CreateNewSet.Params(
            exercise = "",
            muscle = Muscle.Chest,
            workoutDate = 0
        ))

        assertEquals(
            result,
            Result.success(Unit)
        )
    }
}