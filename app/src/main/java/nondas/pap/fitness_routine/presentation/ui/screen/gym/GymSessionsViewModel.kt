package nondas.pap.fitness_routine.presentation.ui.screen.gym

import androidx.lifecycle.viewModelScope
import nondas.pap.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import nondas.pap.fitness_routine.domain.entity.CardioDomainEntity
import nondas.pap.fitness_routine.domain.entity.DailyReportDomainEntity
import nondas.pap.fitness_routine.domain.interactor.bodymeasurement.GetAllBodyMeasurements
import nondas.pap.fitness_routine.domain.interactor.cardio.GetAllCardios
import nondas.pap.fitness_routine.domain.interactor.report.GetDailyReports
import nondas.pap.fitness_routine.presentation.BlocViewModel
import nondas.pap.fitness_routine.presentation.util.toTimeStamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class GymSessionsViewModel @Inject constructor(
    getDailyReports: GetDailyReports,
    getAllCardios: GetAllCardios,
    getAllBodyMeasurements: GetAllBodyMeasurements
): BlocViewModel<GymSessionsEvent, GymSessionsState>() {


    private val dailyReportsFlow = getDailyReports.execute(Unit)
        .map { it.getOrThrow() }
        .catch { addError(it) }

    private val cardiosFlow = getAllCardios.execute(Unit)
        .map { it.getOrThrow() }
        .catch { addError(it) }

    private val measurementsFlow = getAllBodyMeasurements.execute(Unit)
        .map { it.getOrThrow() }
        .catch { addError(it) }

    override val _uiState: StateFlow<GymSessionsState> = combine(
        dailyReportsFlow.onStart { emit(listOf()) },
        cardiosFlow.onStart { emit(listOf()) },
        measurementsFlow.onStart { emit(listOf()) }
    ) { reports, cardios, measurements ->

        val cardiosByDate = cardios.groupBy { it.date }
        val measurementsByDate = measurements.groupBy { it.date }

        val workoutSessions = reports.map { report ->
            WorkoutSession(
                report = report,
                cardios = cardiosByDate[report.date] ?: emptyList(),
                measurement = measurementsByDate[report.date.toTimeStamp()]?.firstOrNull()
            )
        }

        GymSessionsState.Content(
            workoutSessions = workoutSessions
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = GymSessionsState.Idle
    )
}



sealed interface GymSessionsEvent {

}


sealed interface GymSessionsState {
    object Idle : GymSessionsState
    data class Content(val workoutSessions: List<WorkoutSession>) : GymSessionsState
}


data class WorkoutSession(
    val report: DailyReportDomainEntity,
    val cardios: List<CardioDomainEntity>,
    val measurement: BodyMeasurementDomainEntity?
)