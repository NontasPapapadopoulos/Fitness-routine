package com.example.fitness_routine.domain.interactor.cardio


import com.example.fitness_routine.domain.SuspendUseCase
import com.example.fitness_routine.domain.entity.CardioDomainEntity
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.ExerciseDomainEntity
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.CardioRepository
import com.example.fitness_routine.domain.repository.DailyRoutineRepository
import com.example.fitness_routine.domain.repository.ExerciseRepository
import kotlinx.coroutines.CoroutineDispatcher
import java.util.Date
import java.util.UUID
import javax.inject.Inject


open class AddCardio @Inject constructor(
    private val cardioRepository: CardioRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, AddCardio.Params>(dispatcher) {


    override suspend fun invoke(params: Params) {

        val cardio = CardioDomainEntity(
            id = UUID.randomUUID().toString(),
            type = params.type,
            minutes = params.minutes,
            date = params.date
        )

        return cardioRepository.put(cardio)
    }


    data class Params(
        val date: Date,
        val type: String,
        val minutes: String
    )
}