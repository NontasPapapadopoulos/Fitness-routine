package com.example.fitness_routine.presentation.ui.screen.bodymeasurement

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import com.example.fitness_routine.domain.interactor.bodymeasurement.GetBodyMeasurement
import com.example.fitness_routine.domain.interactor.bodymeasurement.UpdateBodyMeasurement
import com.example.fitness_routine.presentation.BlocViewModel
import com.example.fitness_routine.presentation.navigation.NavigationArgument
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class BodyMeasurementViewModel @Inject constructor(
    private val updateBodyMeasurement: UpdateBodyMeasurement,
    private val getBodyMeasurements: GetBodyMeasurement,
    private val savedStateHandle: SavedStateHandle
): BlocViewModel<MeasurementEvent, MeasurementState>() {

    private val date get() = savedStateHandle.get<Long>(NavigationArgument.Date.param)!!



    override val _uiState: StateFlow<MeasurementState> = getBodyMeasurements.execute(GetBodyMeasurement.Params(date))
        .map { it.getOrThrow() }
        .onStart { emit(getBodyMeasurement()) }
        .catch { addError(it) }
        .map { measurement ->

            MeasurementState.Content(measurement)

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = MeasurementState.Idle
        )



    init {

        on(MeasurementEvent.UpdateField::class) {

        }

    }



}


sealed interface MeasurementEvent {
    data class UpdateField(val field: MeasurementField, val value: String): MeasurementEvent
}


sealed interface MeasurementState {
    object Idle: MeasurementState
    data class Content(val bodyMeasurement: BodyMeasurementDomainEntity): MeasurementState
}


enum class MeasurementField {
    Weight,
    Fat,
    MuscleMass,
    BMI,
    TBW,
    BMR,
    VisceralFat,
    MetabolicAge
}

private fun getBodyMeasurement() = BodyMeasurementDomainEntity(
    id = "",
    weight = 0f,
    fat = 0f,
    muscleMass = 0f,
    bmi = 0f,
    tbw = 0f,
    bmr = 0f,
    visceralFat = 0,
    metabolicAge = 0,
    date = 0L
)