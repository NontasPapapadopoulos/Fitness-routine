package nondas.pap.fitness_routine.domain.interactor.bodymeasurement

import nondas.pap.fitness_routine.DummyEntities
import nondas.pap.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import nondas.pap.fitness_routine.domain.repository.BodyMeasurementRepository
import nondas.pap.fitness_routine.measurement
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
class AddBodyMeasurementTest {

    private lateinit var addBodyMeasurement: AddBodyMeasurement


    @Mock
    private lateinit var bodyMeasurementRepository: BodyMeasurementRepository

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        addBodyMeasurement = AddBodyMeasurement(bodyMeasurementRepository, dispatcher)
    }

    @Test
    fun execute_addBodyMeasurement() = runTest {
        whenever(bodyMeasurementRepository.put(any())).thenReturn(Unit)

        val result = addBodyMeasurement.execute(AddBodyMeasurement.Params(
            measurement = DummyEntities.measurement
        ))

        assertEquals(
            result,
            Result.success(Unit)
        )
    }
}