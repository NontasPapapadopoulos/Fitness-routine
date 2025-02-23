package com.example.fitness_routine.domain.interactor.bodymeasurement


import com.example.fitness_routine.domain.SuspendUseCase
import com.example.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import com.example.fitness_routine.domain.entity.CardioDomainEntity
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.BodyMeasurementRepository
import kotlinx.coroutines.CoroutineDispatcher
import java.util.Date
import java.util.UUID
import javax.inject.Inject


open class AddBodyMeasurement @Inject constructor(
    private val bodyMeasurementRepository: BodyMeasurementRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, AddBodyMeasurement.Params>(dispatcher) {

    override suspend fun invoke(params: Params) {
        bodyMeasurementRepository.put(
            params.measurement.copy(
                id = UUID.randomUUID().toString()
            )
        )
    }


    data class Params(
        val measurement: BodyMeasurementDomainEntity
    )
}