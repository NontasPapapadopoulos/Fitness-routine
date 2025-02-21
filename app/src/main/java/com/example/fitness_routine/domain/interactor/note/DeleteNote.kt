package com.example.fitness_routine.domain.interactor.note


import com.example.fitness_routine.data.entity.NoteDataEntity
import com.example.fitness_routine.domain.SuspendUseCase
import com.example.fitness_routine.domain.entity.CheatMealDomainEntity
import com.example.fitness_routine.domain.entity.NoteDomainEntity
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.CheatMealRepository
import com.example.fitness_routine.domain.repository.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


open class DeleteNote @Inject constructor(
    private val noteRepository: NoteRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, DeleteNote.Params>(dispatcher) {


    override suspend fun invoke(params: Params) {
        return noteRepository.delete(params.note)
    }


    data class Params(val note: NoteDomainEntity)
}