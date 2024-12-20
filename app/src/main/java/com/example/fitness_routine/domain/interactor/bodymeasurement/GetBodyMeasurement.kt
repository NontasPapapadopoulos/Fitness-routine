package com.example.fitness_routine.domain.interactor.bodymeasurement

import com.example.fitness_routine.domain.FlowUseCase
import com.example.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.BodyMeasurementRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetBodyMeasurement @Inject constructor(
    private val bodyMeasurementRepository: BodyMeasurementRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<BodyMeasurementDomainEntity, GetBodyMeasurement.Params>(dispatcher) {


    override fun invoke(params: Params): Flow<BodyMeasurementDomainEntity> {
        return bodyMeasurementRepository.getBodyMeasurement(params.date)
    }

    data class Params(val date: Long)

}