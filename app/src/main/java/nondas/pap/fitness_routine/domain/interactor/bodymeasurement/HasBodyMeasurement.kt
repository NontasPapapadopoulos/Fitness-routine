package nondas.pap.fitness_routine.domain.interactor.bodymeasurement

import nondas.pap.fitness_routine.domain.FlowUseCase
import nondas.pap.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import nondas.pap.fitness_routine.domain.executor.IoDispatcher
import nondas.pap.fitness_routine.domain.repository.BodyMeasurementRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


open class HasBodyMeasurement @Inject constructor(
    private val bodyMeasurementRepository: BodyMeasurementRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<Boolean, HasBodyMeasurement.Params>(dispatcher) {


    override fun invoke(params: Params): Flow<Boolean> {
        return bodyMeasurementRepository.hasBodyMeasurement(params.date)
    }

    data class Params(val date: Long)

}