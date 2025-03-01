package com.example.fitness_routine.domain.interactor.note

import com.example.fitness_routine.domain.FlowUseCase
import com.example.fitness_routine.domain.entity.CheatMealDomainEntity
import com.example.fitness_routine.domain.entity.NoteDomainEntity
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.CheatMealRepository
import com.example.fitness_routine.domain.repository.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


open class GetNotes @Inject constructor(
    private val noteRepository: NoteRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<List<NoteDomainEntity>, GetNotes.Params>(dispatcher) {


    override fun invoke(params: Params): Flow<List<NoteDomainEntity>> {
        return noteRepository.getNotes(params.date)
    }

    data class Params(val date: Long)

}