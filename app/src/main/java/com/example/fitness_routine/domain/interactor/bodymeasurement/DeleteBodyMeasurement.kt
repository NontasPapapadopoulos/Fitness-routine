package com.example.fitness_routine.domain.interactor.bodymeasurement


import com.example.fitness_routine.domain.SuspendUseCase
import com.example.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.BodyMeasurementRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


class DeleteBodyMeasurement @Inject constructor(
    private val bodyMeasurementRepository: BodyMeasurementRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, DeleteBodyMeasurement.Params>(dispatcher) {


    override suspend fun invoke(params: Params) {
        return bodyMeasurementRepository.delete(params.measurement)
    }


    data class Params(val measurement: BodyMeasurementDomainEntity)
}