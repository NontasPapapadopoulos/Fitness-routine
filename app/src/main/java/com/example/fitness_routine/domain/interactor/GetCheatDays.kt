package com.example.fitness_routine.domain.interactor

import com.example.fitness_routine.domain.FlowUseCase
import com.example.fitness_routine.domain.entity.CheatMealDomainEntity
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.DailyRoutineRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetCheatDays @Inject constructor(
    private val dailyRoutineRepository: DailyRoutineRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<List<CheatMealDomainEntity>, Unit>(dispatcher) {


    override fun invoke(params: Unit): Flow<List<CheatMealDomainEntity>> {
        return dailyRoutineRepository.getDailyReports()
                .map { report ->
                    report.filter { it.hadCheatMeal }
                        .map { CheatMealDomainEntity(it.date) }
                }

    }
}