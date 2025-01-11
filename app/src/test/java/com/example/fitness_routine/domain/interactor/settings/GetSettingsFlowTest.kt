package com.example.fitness_routine.domain.interactor.settings

import com.example.fitness_routine.DummyEntities
import com.example.fitness_routine.domain.repository.SettingsRepository
import com.example.fitness_routine.settings
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

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetSettingsTest {


    private lateinit var getSettings: GetSettings

    @Mock
    private lateinit var settingsRepository: SettingsRepository

    private val dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        getSettings = GetSettings(settingsRepository, dispatcher)
    }


    @Test
    fun execute_getSettings() = runTest {
        whenever(settingsRepository.getSettings()).thenReturn(flowOf(settings))

        val result = getSettings.execute(Unit).first()

        assertEquals(
            Result.success(settings),
            result
        )
    }



    companion object {
        val settings = DummyEntities.settings
    }
}