package nondas.pap.fitness_routine.domain.interactor.report


import nondas.pap.fitness_routine.domain.SuspendUseCase
import nondas.pap.fitness_routine.domain.entity.DailyReportDomainEntity
import nondas.pap.fitness_routine.domain.executor.IoDispatcher
import nondas.pap.fitness_routine.domain.repository.DailyRoutineRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


open class InitDailyReport @Inject constructor(
    private val dailyRoutineRepository: DailyRoutineRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): SuspendUseCase<Unit, InitDailyReport.Params>(dispatcher) {


    override suspend fun invoke(params: Params) {
        return dailyRoutineRepository.initDailyReport(params.date)
    }


    data class Params(val date: Long)
}