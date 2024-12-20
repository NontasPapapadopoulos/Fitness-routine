package com.example.fitness_routine.domain.interactor.note


import com.example.fitness_routine.domain.SuspendUseCase
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.CheatMealRepository
import com.example.fitness_routine.domain.repository.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


open class InitNote @Inject constructor(
    private val noteRepository: NoteRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, InitNote.Params>(dispatcher) {


    override suspend fun invoke(params: Params) {
        return noteRepository.init(params.date)
    }


    data class Params(val date: Long)
}