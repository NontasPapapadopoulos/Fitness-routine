package nondas.pap.fitness_routine.domain.interactor.cheat

import nondas.pap.fitness_routine.DummyEntities
import nondas.pap.fitness_routine.cheatMeal
import nondas.pap.fitness_routine.domain.repository.CheatMealRepository
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
class UpdateCheatMealTest {

    private lateinit var updateCheatMeal: UpdateCheatMeal


    @Mock
    private lateinit var cheatMealRepository: CheatMealRepository

    private var dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        updateCheatMeal = UpdateCheatMeal(cheatMealRepository, dispatcher)
    }


    @Test
    fun execute_addCheatMeal() = runTest {
        whenever(cheatMealRepository.update(any())).thenReturn(Unit)

        val result = updateCheatMeal.execute(UpdateCheatMeal.Params(DummyEntities.cheatMeal))


        assertEquals(
            result,
            Result.success(Unit)
        )
    }
}