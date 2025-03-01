package nondas.pap.fitness_routine.domain.interactor.note

import nondas.pap.fitness_routine.domain.FlowUseCase
import nondas.pap.fitness_routine.domain.entity.CheatMealDomainEntity
import nondas.pap.fitness_routine.domain.entity.NoteDomainEntity
import nondas.pap.fitness_routine.domain.executor.IoDispatcher
import nondas.pap.fitness_routine.domain.repository.CheatMealRepository
import nondas.pap.fitness_routine.domain.repository.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


open class GetAllNotes @Inject constructor(
    private val noteRepository: NoteRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<List<NoteDomainEntity>, Unit>(dispatcher) {


    override fun invoke(params: Unit): Flow<List<NoteDomainEntity>> {
        return noteRepository.getNotes()
    }


}