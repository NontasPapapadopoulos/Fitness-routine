package com.example.fitness_routine.domain.interactor.set

import com.example.fitness_routine.domain.FlowUseCase
import com.example.fitness_routine.domain.entity.CheatMealDomainEntity
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.ExerciseDomainEntity
import com.example.fitness_routine.domain.entity.SetDomainEntity
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.DailyRoutineRepository
import com.example.fitness_routine.domain.repository.ExerciseRepository
import com.example.fitness_routine.domain.repository.SetRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetAllSets @Inject constructor(
    private val setRepository: SetRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<List<SetDomainEntity>, Unit>(dispatcher) {


    override fun invoke(params: Unit): Flow<List<SetDomainEntity>> {
        return setRepository.getAllSets()

    }

}