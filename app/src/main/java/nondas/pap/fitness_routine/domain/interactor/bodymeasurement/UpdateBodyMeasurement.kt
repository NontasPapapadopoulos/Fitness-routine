package nondas.pap.fitness_routine.domain.interactor.bodymeasurement


import nondas.pap.fitness_routine.domain.SuspendUseCase
import nondas.pap.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import nondas.pap.fitness_routine.domain.executor.IoDispatcher
import nondas.pap.fitness_routine.domain.repository.BodyMeasurementRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


open class UpdateBodyMeasurement @Inject constructor(
    private val bodyMeasurementRepository: BodyMeasurementRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, UpdateBodyMeasurement.Params>(dispatcher) {

    override suspend fun invoke(params: Params) {
        return bodyMeasurementRepository.update(params.measurement)
    }


    data class Params(val measurement: BodyMeasurementDomainEntity)
}