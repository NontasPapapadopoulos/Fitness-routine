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
class AddCheatMealTest {

    private lateinit var addCheatMeal: AddCheatMeal


    @Mock
    private lateinit var cheatMealRepository: CheatMealRepository

    private var dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        addCheatMeal = AddCheatMeal(cheatMealRepository, dispatcher)
    }


    @Test
    fun execute_addCheatMeal() = runTest {
        whenever(cheatMealRepository.put(any())).thenReturn(Unit)

        val result = addCheatMeal.execute(AddCheatMeal.Params(date = Date(), "meal"))


        assertEquals(
            result,
            Result.success(Unit)
        )
    }
}