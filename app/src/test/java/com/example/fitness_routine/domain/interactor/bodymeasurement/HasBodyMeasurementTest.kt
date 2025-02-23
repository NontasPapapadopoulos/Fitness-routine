package com.example.fitness_routine.domain.interactor.bodymeasurement

import com.example.fitness_routine.DummyEntities
import com.example.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import com.example.fitness_routine.domain.repository.BodyMeasurementRepository
import com.example.fitness_routine.measurement
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
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class HasBodyMeasurementTest {

    private lateinit var hasBodyMeasurement: HasBodyMeasurement


    @Mock
    private lateinit var bodyMeasurementRepository: BodyMeasurementRepository

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        hasBodyMeasurement = HasBodyMeasurement(bodyMeasurementRepository, dispatcher)
    }

    @Test
    fun execute_hasBodyMeasurement() = runTest {
        whenever(bodyMeasurementRepository.hasBodyMeasurement(any())).thenReturn(flowOf(true))

        val result = hasBodyMeasurement.execute(HasBodyMeasurement.Params(date = 0)).first()

        assertEquals(
            result,
            Result.success(true)
        )
    }
}