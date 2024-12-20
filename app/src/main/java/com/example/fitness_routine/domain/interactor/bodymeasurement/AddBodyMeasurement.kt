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


class AddBodyMeasurement @Inject constructor(
    private val bodyMeasurementRepository: BodyMeasurementRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, AddBodyMeasurement.Params>(dispatcher) {

    override suspend fun invoke(params: Params) {

        val measurement = BodyMeasurementDomainEntity(
            id = UUID.randomUUID().toString(),
            date = params.date,
            weight = params.weight,
            fat = params.fat,
            muscleMass = params.muscleMass,
            bmi = params.bmi,
            tbw = params.tbw,
            bmr = params.bmr,
            visceralFat = params.visceralFat,
            metabolicAge = params.metabolicAge
        )

        return bodyMeasurementRepository.put(measurement)
    }


    data class Params(
        val date: Long,
        val weight: Float,
        val fat: Float,
        val muscleMass: Float,
        val bmi: Float,
        val tbw: Float,
        val bmr: Float,
        val visceralFat: Int,
        val metabolicAge: Int
    )
}