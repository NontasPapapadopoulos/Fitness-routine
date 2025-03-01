package nondas.pap.fitness_routine.domain.interactor.note


import nondas.pap.fitness_routine.domain.SuspendUseCase
import nondas.pap.fitness_routine.domain.entity.NoteDomainEntity
import nondas.pap.fitness_routine.domain.executor.IoDispatcher
import nondas.pap.fitness_routine.domain.repository.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


open class UpdateNote @Inject constructor(
    private val noteRepository: NoteRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, UpdateNote.Params>(dispatcher) {

    override suspend fun invoke(params: Params) {
        return noteRepository.update(params.note)
    }


    data class Params(val note: NoteDomainEntity)
}