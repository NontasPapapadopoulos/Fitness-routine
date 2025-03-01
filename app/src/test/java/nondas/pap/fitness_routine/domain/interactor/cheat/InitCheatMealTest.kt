package nondas.pap.fitness_routine.domain.interactor.cheat

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
class InitCheatMealTest {

    private lateinit var initCheatMeal: InitCheatMeal


    @Mock
    private lateinit var cheatMealRepository: CheatMealRepository

    private var dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        initCheatMeal = InitCheatMeal(cheatMealRepository, dispatcher)
    }


    @Test
    fun execute_addCheatMeal() = runTest {
        whenever(cheatMealRepository.init(any())).thenReturn(Unit)

        val result = initCheatMeal.execute(InitCheatMeal.Params(0))


        assertEquals(
            result,
            Result.success(Unit)
        )
    }
}