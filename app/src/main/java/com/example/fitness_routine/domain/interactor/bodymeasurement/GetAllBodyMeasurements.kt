package com.example.fitness_routine.domain.interactor.bodymeasurement

import com.example.fitness_routine.domain.FlowUseCase
import com.example.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import com.example.fitness_routine.domain.entity.CardioDomainEntity
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.BodyMeasurementRepository
import com.example.fitness_routine.domain.repository.CardioRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAllBodyMeasurements @Inject constructor(
    private val bodyMeasurementRepository: BodyMeasurementRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<List<BodyMeasurementDomainEntity>, Unit>(dispatcher) {


    override fun invoke(params: Unit): Flow<List<BodyMeasurementDomainEntity>> {
        return bodyMeasurementRepository.getBodyMeasurements()
    }


}