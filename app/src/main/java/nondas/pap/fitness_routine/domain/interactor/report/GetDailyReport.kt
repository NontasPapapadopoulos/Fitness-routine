package nondas.pap.fitness_routine.domain.interactor.report

import nondas.pap.fitness_routine.domain.FlowUseCase
import nondas.pap.fitness_routine.domain.entity.DailyReportDomainEntity
import nondas.pap.fitness_routine.domain.executor.IoDispatcher
import nondas.pap.fitness_routine.domain.repository.DailyRoutineRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject


open class GetDailyReport @Inject constructor(
    private val dailyRoutineRepository: DailyRoutineRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<DailyReportDomainEntity, GetDailyReport.Params>(dispatcher) {


    override  fun invoke(params: Params): Flow<DailyReportDomainEntity> {
        return dailyRoutineRepository.getDailyReport(params.date)
    }


    data class Params(val date: Long)
}