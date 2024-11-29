package com.example.fitness_routine.domain.interactor.cardio

import com.example.fitness_routine.domain.FlowUseCase
import com.example.fitness_routine.domain.entity.CardioDomainEntity
import com.example.fitness_routine.domain.entity.CheatMealDomainEntity
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.ExerciseDomainEntity
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.CardioRepository
import com.example.fitness_routine.domain.repository.DailyRoutineRepository
import com.example.fitness_routine.domain.repository.ExerciseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject


class GetAllCardios @Inject constructor(
    private val cardioRepository: CardioRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<List<CardioDomainEntity>, Unit>(dispatcher) {


    override fun invoke(params: Unit): Flow<List<CardioDomainEntity>> {
        return cardioRepository.getCardios()
    }


}