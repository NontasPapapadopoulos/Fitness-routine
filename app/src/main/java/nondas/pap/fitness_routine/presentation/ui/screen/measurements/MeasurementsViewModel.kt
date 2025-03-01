package nondas.pap.fitness_routine.presentation.ui.screen.measurements

import androidx.lifecycle.viewModelScope
import nondas.pap.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import nondas.pap.fitness_routine.domain.interactor.bodymeasurement.GetAllBodyMeasurements
import nondas.pap.fitness_routine.presentation.BlocViewModel
import nondas.pap.fitness_routine.presentation.ui.screen.bodymeasurement.MeasurementState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class MeasurementsViewModel @Inject constructor(
    private val getAllBodyMeasurements: GetAllBodyMeasurements
): BlocViewModel<MeasurementsEvent, MeasurementsState>() {



    override val _uiState: StateFlow<MeasurementsState> = getAllBodyMeasurements.execute(Unit)
        .map { it.getOrThrow() }
        .onStart { emit(listOf()) }
        .catch { addError(it) }
        .map { measurements ->
            MeasurementsState.Content(measurements)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = MeasurementsState.Idle
        )


}




sealed interface MeasurementsEvent {

}



sealed interface MeasurementsState {
    object Idle: MeasurementsState
    data class Content(
        val measurements: List<BodyMeasurementDomainEntity>
    ): MeasurementsState

}