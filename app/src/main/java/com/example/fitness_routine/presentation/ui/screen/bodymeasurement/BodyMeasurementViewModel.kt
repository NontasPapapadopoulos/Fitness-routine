package com.example.fitness_routine.presentation.ui.screen.bodymeasurement

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.fitness_routine.data.util.toDate
import com.example.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import com.example.fitness_routine.domain.interactor.bodymeasurement.AddBodyMeasurement
import com.example.fitness_routine.domain.interactor.bodymeasurement.GetBodyMeasurement
import com.example.fitness_routine.domain.interactor.bodymeasurement.HasBodyMeasurement
import com.example.fitness_routine.domain.interactor.bodymeasurement.UpdateBodyMeasurement
import com.example.fitness_routine.presentation.BlocViewModel
import com.example.fitness_routine.presentation.navigation.NavigationArgument
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class BodyMeasurementViewModel @Inject constructor(
    private val addBodyMeasurement: AddBodyMeasurement,
    private val updateBodyMeasurement: UpdateBodyMeasurement,
    private val getBodyMeasurements: GetBodyMeasurement,
    private val hasBodyMeasurement: HasBodyMeasurement,
    private val savedStateHandle: SavedStateHandle
): BlocViewModel<MeasurementEvent, MeasurementState>() {

    private val date get() = savedStateHandle.get<Long>(NavigationArgument.Date.param)!!

    private val bodyMeasurementFlow = MutableSharedFlow<BodyMeasurementDomainEntity>()

    private val  existingBodyMeasurementFlow = getBodyMeasurements.execute(GetBodyMeasurement.Params(date))
        .map { it.getOrThrow() }
        .filterNotNull()

    private val hasBodyMeasurementFlow = hasBodyMeasurement.execute(HasBodyMeasurement.Params(date))
        .map { it.getOrThrow() }
        .catch { addError(it) }


    override val _uiState: StateFlow<MeasurementState> = combine(
        hasBodyMeasurementFlow.onStart { emit(false) },
        merge(
            existingBodyMeasurementFlow,
            bodyMeasurementFlow
        ).onStart { emit(getBodyMeasurement(date.toDate())) }
    ) { hasMeasurement, measurement ->

            MeasurementState.Content(
                hasBodyMeasurement = hasMeasurement,
                bodyMeasurement = measurement
            )

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = MeasurementState.Idle
        )



    init {
        on(MeasurementEvent.UpdateField::class) {
            onState<MeasurementState.Content>{ state ->
                val measurement = state.bodyMeasurement
                val updatedMeasurement = when(it.field) {
                    MeasurementField.Weight -> measurement.copy(weight = it.value)
                    MeasurementField.Fat -> measurement.copy(fat = it.value)
                    MeasurementField.MuscleMass -> measurement.copy(muscleMass = it.value)
                    MeasurementField.BMI -> measurement.copy(bmi = it.value)
                    MeasurementField.TBW -> measurement.copy(tbw = it.value)
                    MeasurementField.BMR -> measurement.copy(bmr = it.value)
                    MeasurementField.VisceralFat -> measurement.copy(visceralFat = it.value)
                    MeasurementField.MetabolicAge -> measurement.copy(metabolicAge = it.value)
                }

                bodyMeasurementFlow.emit(updatedMeasurement)

            }
        }


        on(MeasurementEvent.Update::class) {
            onState<MeasurementState.Content> { state ->
                val measurement = state.bodyMeasurement
                updateBodyMeasurement.execute(UpdateBodyMeasurement.Params(measurement))
                    .fold(
                        onSuccess = {},
                        onFailure = { addError(it) }
                    )
            }
        }


        on(MeasurementEvent.Add::class) {
            onState<MeasurementState.Content> { state ->
                val measurement = state.bodyMeasurement
                addBodyMeasurement.execute(AddBodyMeasurement.Params(measurement))
                    .fold(
                        onSuccess = {},
                        onFailure = { addError(it) }
                    )
            }
        }

    }

}


sealed interface MeasurementEvent {
    data class UpdateField(val field: MeasurementField, val value: String): MeasurementEvent
    object Update: MeasurementEvent
    object Add: MeasurementEvent
}


sealed interface MeasurementState {
    object Idle: MeasurementState
    data class Content(
        val bodyMeasurement: BodyMeasurementDomainEntity,
        val hasBodyMeasurement: Boolean
    ): MeasurementState
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

private fun getBodyMeasurement(date: Date) = BodyMeasurementDomainEntity(
    id = "",
    weight = "",
    fat = "",
    muscleMass = "",
    bmi = "",
    tbw = "",
    bmr = "",
    visceralFat = "",
    metabolicAge = "",
    date = date,
    userId = ""
)