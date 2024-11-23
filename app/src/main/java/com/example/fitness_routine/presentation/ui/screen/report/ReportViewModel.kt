package com.example.fitness_routine.presentation.ui.screen.report

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.domain.interactor.report.AddDailyReport
import com.example.fitness_routine.domain.interactor.report.DeleteDailyReport
import com.example.fitness_routine.domain.interactor.report.GetDailyReport
import com.example.fitness_routine.domain.interactor.report.InitDailyReport
import com.example.fitness_routine.domain.interactor.report.UpdateDailyReport
import com.example.fitness_routine.presentation.BlocViewModel
import com.example.fitness_routine.presentation.navigation.NavigationArgument
import com.example.fitness_routine.presentation.util.toDate
import com.example.fitness_routine.presentation.util.toList
import com.example.fitness_routine.presentation.util.toMuscles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
open class ReportViewModel @Inject constructor(
    getDailyReport: GetDailyReport,
    private val deleteReport: DeleteDailyReport,
    private val updateReport: UpdateDailyReport,
    private val createReport: AddDailyReport,
    private val savedStateHandle: SavedStateHandle,
    private val initDailyReport: InitDailyReport,
): BlocViewModel<ReportEvent, ReportState>() {

    private val date get() = savedStateHandle.get<Long>(NavigationArgument.Date.param)!!

    private val cheatMealFlow = MutableSharedFlow<String>()
    private val gymNotesFlow = MutableSharedFlow<String>()
    private val proteinGramsFlow = MutableSharedFlow<String>()
    private val cardioMinutesFlow = MutableSharedFlow<String>()
    private val litersOfWaterFlow = MutableSharedFlow<String>()

    private val  dailyReportFlow = getDailyReport.execute(GetDailyReport.Params(date = date.toDate()))
        .map { it.getOrThrow() }
        .catch { addError(it) }


    override val _uiState: StateFlow<ReportState> = suspend { initDailyReport() }.asFlow()
        .flatMapLatest { dailyReportFlow }.map { report ->
            ReportState.Content(
                date = date,
                dailyReport = report
            )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ReportState.Idle
    )


    init {
        on(ReportEvent.DeleteReport::class) {
            onState<ReportState.Content> { state ->
                deleteReport.execute(DeleteDailyReport.Params(state.dailyReport))
            }
        }

        on(ReportEvent.UpdateReport::class) {
            onState<ReportState.Content> { state ->
                updateReport.execute(UpdateDailyReport.Params(state.dailyReport))
            }
        }

        on(ReportEvent.UpdateCheckBox::class) {
            onState<ReportState.Content> { state ->

                val dailyReport = when(it.checkBoxField) {
                    CheckBoxField.Creatine -> state.dailyReport.copy(hadCreatine = it.isChecked)
                    CheckBoxField.Workout -> state.dailyReport.copy(performedWorkout = it.isChecked)
                    CheckBoxField.CheatMeal -> state.dailyReport.copy(hadCheatMeal = it.isChecked)
                }

                updateReport.execute(UpdateDailyReport.Params(dailyReport))
            }
        }



        on(clazz = ReportEvent.UpdateField::class) {
            onState<ReportState.Content> { state ->

                val dailyReport = when(it.field) {
                    Field.GymNotes -> state.dailyReport.copy(gymNotes = it.value)
                    Field.SleepQuality -> state.dailyReport.copy(sleepQuality = it.value)
                    Field.LitersOfWater -> state.dailyReport.copy(litersOfWater = it.value)
                    Field.TrainedMuscles -> state.dailyReport.copy(musclesTrained = it.value.toList())
                    Field.CardioMinutes -> state.dailyReport.copy(cardioMinutes = it.value)
                    Field.ProteinGrams -> state.dailyReport.copy(proteinGrams = it.value)
                    Field.CheatMeal -> state.dailyReport.copy(meal = it.value)
                    Field.Cardio -> state.dailyReport.copy(cardio = it.value)
                }

                updateReport.execute(UpdateDailyReport.Params(dailyReport))

            }
        }

        on(ReportEvent.SelectMuscle::class) {
            onState<ReportState.Content> { state ->

                val dailyReport = state.dailyReport
                val updatedMuscles = dailyReport.musclesTrained
                    .filterNot { it.isEmpty() }
                    .toMutableList()
                    .apply {
                        if (contains(it.muscle)) remove(it.muscle) else add(it.muscle)
                }.toList()


                updateReport.execute(
                    UpdateDailyReport.Params(
                        dailyReport.copy(musclesTrained = updatedMuscles)
                    )
                )
            }
        }
    }

    private suspend fun initDailyReport() {
        initDailyReport.execute(InitDailyReport.Params(date)).fold(
            onSuccess = {}, onFailure = { addError(it) }
        )
    }

}


sealed interface ReportEvent {
    object DeleteReport: ReportEvent
    object UpdateReport: ReportEvent

    data class UpdateCheckBox(val isChecked: Boolean, val checkBoxField: CheckBoxField): ReportEvent

    data class UpdateField(val value: String, val field: Field): ReportEvent

    data class SelectMuscle(val muscle: String): ReportEvent
}


sealed interface ReportState {

    object Idle: ReportState

    data class Content(
        val date: Long,
        val dailyReport: DailyReportDomainEntity
    ): ReportState

}

enum class CheckBoxField {
    Creatine,
    Workout,
    CheatMeal
}

enum class Field {
    GymNotes,
    SleepQuality,
    LitersOfWater,
    TrainedMuscles,
    CardioMinutes,
    ProteinGrams,
    CheatMeal,
    Cardio
}