package com.example.fitness_routine.presentation.screen.report

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.interactor.report.AddDailyReport
import com.example.fitness_routine.domain.interactor.report.DeleteDailyReport
import com.example.fitness_routine.domain.interactor.report.GetDailyReport
import com.example.fitness_routine.domain.interactor.report.UpdateDailyReport
import com.example.fitness_routine.presentation.BlocViewModel
import com.example.fitness_routine.presentation.navigation.NavigationArgument
import com.example.fitness_routine.presentation.toDate
import com.example.fitness_routine.presentation.toList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class ReportViewModel @Inject constructor(
    getDailyReport: GetDailyReport,
    private val deleteReport: DeleteDailyReport,
    private val updateReport: UpdateDailyReport,
    private val createReport: AddDailyReport,
    private val savedStateHandle: SavedStateHandle
): BlocViewModel<ReportEvent, ReportState>() {

    private val date get() = savedStateHandle.get<Long>(NavigationArgument.Date.param)!!

    private val  dailyReportFlow = getDailyReport.execute(GetDailyReport.Params(date = date.toDate()))
        .map { it.getOrThrow() }
        .catch { addError(it) }

    private val performedWorkoutFlow = MutableSharedFlow<Boolean>()
    private val hadCreatineFlow = MutableSharedFlow<Boolean>()
    private val hadCheatMealFlow = MutableSharedFlow<Boolean>()

    private val gymNotesFlow = MutableSharedFlow<String>()
    private val musclesTrainedFlow = MutableSharedFlow<String>()
    private val sleepQualityFlow = MutableSharedFlow<String>()
    private val proteinGramsFlow = MutableSharedFlow<String>()
    private val cardioMinutesFlow = MutableSharedFlow<String>()
    private val litersOfWaterFlow = MutableSharedFlow<String>()


    override val _uiState: StateFlow<ReportState> = combine(
        dailyReportFlow,
        performedWorkoutFlow.onStart { emit(false) },
        hadCreatineFlow.onStart { emit(false) },
        hadCheatMealFlow.onStart { emit(false) },
        gymNotesFlow.onStart { emit("") },
        sleepQualityFlow.onStart { emit("0") },
        proteinGramsFlow.onStart { emit("0") },
        cardioMinutesFlow.onStart { emit("0") },
        litersOfWaterFlow.onStart { emit("0") },
        musclesTrainedFlow.onStart { emit("") },
    ) { arrayOfValues ->


        ReportState.Content(
            date = date.toString(),
            dailyReport = arrayOfValues[0] as DailyReportDomainEntity,
            performedWorkout = arrayOfValues[1] as Boolean,
            hadCreatine = arrayOfValues[2] as Boolean,
            hadCheatMeal = arrayOfValues[3] as Boolean,
            notes = arrayOfValues[4] as String,
            sleepQuality = arrayOfValues[5] as String,
            proteinGrams = arrayOfValues[6] as String,
            cardioMinutes = arrayOfValues[7] as String,
            litersOfWater = arrayOfValues[8] as String,
            trainedMuscles = arrayOfValues[9] as String,
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
                when(it.checkBoxField) {
                    CheckBoxField.Creatine -> hadCreatineFlow.emit(it.isChecked)
                    CheckBoxField.Workout -> performedWorkoutFlow.emit(it.isChecked)
                    CheckBoxField.CheatMeal -> hadCheatMealFlow.emit(it.isChecked)
                }

                val dailyReport = when(it.checkBoxField) {
                    CheckBoxField.Creatine -> state.dailyReport.copy(hadCreatine = it.isChecked)
                    CheckBoxField.Workout -> state.dailyReport.copy(performedWorkout = it.isChecked)
                    CheckBoxField.CheatMeal -> state.dailyReport.copy(hadCheatMeal = it.isChecked)
                }

                updateReport.execute(UpdateDailyReport.Params(dailyReport))
            }
        }



        on(ReportEvent.UpdateField::class) {
            onState<ReportState.Content> { state ->
                when(it.field) {
                    Field.GymNotes -> gymNotesFlow.emit(it.value)
                    Field.SleepQuality -> sleepQualityFlow.emit(it.value)
                    Field.LitersOfWater -> litersOfWaterFlow.emit(it.value)
                    Field.TrainedMuscles -> musclesTrainedFlow.emit(it.value)
                    Field.CardioMinutes -> cardioMinutesFlow.emit(it.value)
                    Field.ProteinGrams -> proteinGramsFlow.emit(it.value)
                }

                val dailyReport = when(it.field) {
                    Field.GymNotes -> state.dailyReport.copy(gymNotes = it.value)
                    Field.SleepQuality -> state.dailyReport.copy(sleepQuality = it.value)
                    Field.LitersOfWater -> state.dailyReport.copy(litersOfWater = it.value)
                    Field.TrainedMuscles -> state.dailyReport.copy(musclesTrained = it.value.toList())
                    Field.CardioMinutes -> state.dailyReport.copy(cardioMinutes = it.value)
                    Field.ProteinGrams -> state.dailyReport.copy(proteinGrams = it.value)
                }

                updateReport.execute(UpdateDailyReport.Params(dailyReport))

            }
        }
    }

}


sealed interface ReportEvent {
    object DeleteReport: ReportEvent
    object UpdateReport: ReportEvent

    data class UpdateCheckBox(val isChecked: Boolean, val checkBoxField: CheckBoxField): ReportEvent

    data class UpdateField(val value: String, val field: Field): ReportEvent
}


sealed interface ReportState {

    object Idle: ReportState

    data class Content(
        val date: String,
        val notes: String,
        val proteinGrams: String,
        val cardioMinutes: String,
        val sleepQuality: String,
        val performedWorkout: Boolean,
        val hadCreatine: Boolean,
        val hadCheatMeal: Boolean,
        val trainedMuscles: String,
        val litersOfWater: String,
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
    ProteinGrams
}