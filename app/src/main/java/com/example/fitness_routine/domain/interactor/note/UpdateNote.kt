package com.example.fitness_routine.domain.interactor.note


import com.example.fitness_routine.domain.SuspendUseCase
import com.example.fitness_routine.domain.entity.NoteDomainEntity
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


class UpdateNote @Inject constructor(
    private val noteRepository: NoteRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, UpdateNote.Params>(dispatcher) {

    override suspend fun invoke(params: Params) {
        return noteRepository.update(params.note)
    }


    data class Params(val note: NoteDomainEntity)
}