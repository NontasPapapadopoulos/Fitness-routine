package nondas.pap.fitness_routine.domain.interactor.bodymeasurement

import nondas.pap.fitness_routine.domain.FlowUseCase
import nondas.pap.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import nondas.pap.fitness_routine.domain.entity.CardioDomainEntity
import nondas.pap.fitness_routine.domain.executor.IoDispatcher
import nondas.pap.fitness_routine.domain.repository.BodyMeasurementRepository
import nondas.pap.fitness_routine.domain.repository.CardioRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


open class GetAllBodyMeasurements @Inject constructor(
    private val bodyMeasurementRepository: BodyMeasurementRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<List<BodyMeasurementDomainEntity>, Unit>(dispatcher) {


    override fun invoke(params: Unit): Flow<List<BodyMeasurementDomainEntity>> {
        return bodyMeasurementRepository.getBodyMeasurements()
    }


}