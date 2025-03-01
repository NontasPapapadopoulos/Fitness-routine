package nondas.pap.fitness_routine.domain.interactor.settings

import nondas.pap.fitness_routine.DummyEntities
import nondas.pap.fitness_routine.domain.repository.SettingsRepository
import nondas.pap.fitness_routine.settings
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
class ChangeSettingsTest {

    private lateinit var changeSettings: ChangeSettings

    @Mock
    private lateinit var settingsRepository: SettingsRepository

    private val dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        changeSettings = ChangeSettings(settingsRepository, dispatcher)
    }


    @Test
    fun execute_changeSettings() = runTest {
        whenever(settingsRepository.changeSettings(any())).thenReturn(Unit)

        val result = changeSettings.execute(ChangeSettings.Params(settings))

        assertEquals(
            Result.success(Unit),
            result
        )
    }



    companion object {
        val settings = DummyEntities.settings
    }
}