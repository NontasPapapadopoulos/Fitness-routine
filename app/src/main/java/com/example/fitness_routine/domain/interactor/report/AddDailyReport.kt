package com.example.fitness_routine.domain.interactor.report


import com.example.fitness_routine.domain.SuspendUseCase
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.executor.IoDispatcher
import com.example.fitness_routine.domain.repository.DailyRoutineRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


open class AddDailyReport @Inject constructor(
    private val dailyRoutineRepository: DailyRoutineRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, AddDailyReport.Params>(dispatcher) {


    override suspend fun invoke(params: Params) {
        return dailyRoutineRepository.delete(params.report)
    }


    data class Params(val report: DailyReportDomainEntity)
}