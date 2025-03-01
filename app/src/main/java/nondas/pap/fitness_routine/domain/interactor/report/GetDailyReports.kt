package nondas.pap.fitness_routine.domain.interactor.report

import nondas.pap.fitness_routine.domain.FlowUseCase
import nondas.pap.fitness_routine.domain.entity.DailyReportDomainEntity
import nondas.pap.fitness_routine.domain.executor.IoDispatcher
import nondas.pap.fitness_routine.domain.repository.DailyRoutineRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


open class GetDailyReports @Inject constructor(
    private val dailyRoutineRepository: DailyRoutineRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<List<DailyReportDomainEntity>, Unit>(dispatcher) {


    override fun invoke(params: Unit): Flow<List<DailyReportDomainEntity>> {
        return dailyRoutineRepository.getDailyReports()
    }
}