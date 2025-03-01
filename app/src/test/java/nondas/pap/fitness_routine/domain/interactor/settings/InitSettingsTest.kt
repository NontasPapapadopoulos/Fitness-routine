package com.example.fitness_routine.domain.interactor.settings

import com.example.fitness_routine.domain.repository.SettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class InitSettingsTest {

    private lateinit var initSettings: InitSettings

    @Mock
    private lateinit var settingsRepository: SettingsRepository

    private var dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        initSettings = InitSettings(settingsRepository, dispatcher)
    }


    @Test
    fun execute_initScan() = runTest {
        whenever(settingsRepository.init()).thenReturn(Unit)

        val result = initSettings.execute(Unit)

        assertEquals(
            result,
            Result.success(Unit)
        )
    }

}