package nondas.pap.fitness_routine.domain.interactor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import nondas.pap.fitness_routine.domain.SuspendUseCase
import nondas.pap.fitness_routine.domain.executor.IoDispatcher
import nondas.pap.fitness_routine.domain.repository.CardioRepository
import javax.inject.Inject

class CreatePDF @Inject constructor(
    private val cardioRepository: CardioRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, CreatePDF.Params>(dispatcher) {


    override suspend fun invoke(params: CreatePDF.Params) {
        cardioRepository.getCardios().first()

        
    }


    data class Params(val xx: String)
}