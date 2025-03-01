package nondas.pap.fitness_routine.domain.interactor.settings

import nondas.pap.fitness_routine.domain.entity.enums.Choice
import nondas.pap.fitness_routine.domain.repository.SettingsRepository
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
class ChangeChoiceTest {

    private lateinit var changeChoice: ChangeChoice

    @Mock
    private lateinit var settingsRepository: SettingsRepository

    private var dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        changeChoice = ChangeChoice(settingsRepository, dispatcher)
    }



    @Test
    fun execute_changeChoice() = runTest {
        whenever(settingsRepository.changeChoice(any())).thenReturn(Unit)

        val result = changeChoice.execute(ChangeChoice.Params(Choice.Cheat))


        assertEquals(
            result,
            Result.success(Unit)
        )
    }
}