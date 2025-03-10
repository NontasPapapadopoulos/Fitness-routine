package nondas.pap.fitness_routine.domain.interactor.note


import nondas.pap.fitness_routine.domain.SuspendUseCase
import nondas.pap.fitness_routine.domain.entity.NoteDomainEntity
import nondas.pap.fitness_routine.domain.executor.IoDispatcher
import nondas.pap.fitness_routine.domain.repository.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import java.util.Date
import java.util.UUID
import javax.inject.Inject


open class AddNote @Inject constructor(
    private val noteRepository: NoteRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, AddNote.Params>(dispatcher) {

    override suspend fun invoke(params: Params) {

        val note = NoteDomainEntity(
            id = UUID.randomUUID().toString(),
            date = params.date,
            text = params.note
        )

        return noteRepository.put(note)
    }


    data class Params(
        val date: Date,
        val note: String,
    )
}