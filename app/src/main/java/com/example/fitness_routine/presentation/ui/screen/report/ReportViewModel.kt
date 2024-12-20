package com.example.fitness_routine.presentation.ui.screen.report

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.fitness_routine.data.entity.CheatMealDataEntity
import com.example.fitness_routine.domain.entity.CardioDomainEntity
import com.example.fitness_routine.domain.entity.CheatMealDomainEntity
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.NoteDomainEntity
import com.example.fitness_routine.domain.interactor.cardio.AddCardio
import com.example.fitness_routine.domain.interactor.cardio.DeleteCardio
import com.example.fitness_routine.domain.interactor.cardio.GetCardios
import com.example.fitness_routine.domain.interactor.cardio.InitCardio
import com.example.fitness_routine.domain.interactor.cardio.UpdateCardio
import com.example.fitness_routine.domain.interactor.cheat.AddCheatMeal
import com.example.fitness_routine.domain.interactor.cheat.DeleteCheatMeal
import com.example.fitness_routine.domain.interactor.cheat.GetCheatMeals
import com.example.fitness_routine.domain.interactor.cheat.InitCheatMeal
import com.example.fitness_routine.domain.interactor.cheat.UpdateCheatMeal
import com.example.fitness_routine.domain.interactor.note.AddNote
import com.example.fitness_routine.domain.interactor.note.DeleteNote
import com.example.fitness_routine.domain.interactor.note.GetNotes
import com.example.fitness_routine.domain.interactor.note.InitNote
import com.example.fitness_routine.domain.interactor.note.UpdateNote
import com.example.fitness_routine.domain.interactor.report.AddDailyReport
import com.example.fitness_routine.domain.interactor.report.DeleteDailyReport
import com.example.fitness_routine.domain.interactor.report.GetDailyReport
import com.example.fitness_routine.domain.interactor.report.InitDailyReport
import com.example.fitness_routine.domain.interactor.report.UpdateDailyReport
import com.example.fitness_routine.presentation.BlocViewModel
import com.example.fitness_routine.presentation.navigation.NavigationArgument
import com.example.fitness_routine.presentation.util.toDate
import com.example.fitness_routine.presentation.util.toList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
open class ReportViewModel @Inject constructor(
    getDailyReport: GetDailyReport,
    private val deleteReport: DeleteDailyReport,
    private val updateReport: UpdateDailyReport,
    private val savedStateHandle: SavedStateHandle,
    private val initDailyReport: InitDailyReport,
    private val initCardio: InitCardio,
    private val getCardios: GetCardios,
    private val deleteCardio: DeleteCardio,
    private val updateCardio: UpdateCardio,
    private val addCardio: AddCardio,
    private val getCheatMeals: GetCheatMeals,
    private val getNotes: GetNotes,
    private val initNote: InitNote,
    private val initCheatMeal: InitCheatMeal,
    private val addCheatMeal: AddCheatMeal,
    private val deleteCheatMeal: DeleteCheatMeal,
    private val updateCheatMeal: UpdateCheatMeal,
    private val addNote: AddNote,
    private val deleteNote: DeleteNote,
    private val updateNote: UpdateNote
): BlocViewModel<ReportEvent, ReportState>() {

    private val date get() = savedStateHandle.get<Long>(NavigationArgument.Date.param)!!

    private val  dailyReportFlow = getDailyReport.execute(GetDailyReport.Params(date = date))
        .map { it.getOrThrow() }
        .catch { addError(it) }

    private val cardiosFlow = getCardios.execute(GetCardios.Params(date = date))
        .map { it.getOrThrow() }
        .catch { addError(it) }

    private val cheatMealsFlow = getCheatMeals.execute(GetCheatMeals.Params(date = date))
        .map { it.getOrThrow() }
        .catch { addError(it) }

    private val notesFlow = getNotes.execute(GetNotes.Params(date = date))
        .map { it.getOrThrow() }
        .catch { addError(it) }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val _uiState: StateFlow<ReportState> = combine(
        suspend { initCardio() }.asFlow().flatMapLatest { cardiosFlow },
        suspend { initDailyReport() }.asFlow().flatMapLatest { dailyReportFlow },
        suspend { initNote() }.asFlow().flatMapLatest { notesFlow },
        suspend { initCheatMeal() }.asFlow().flatMapLatest { cheatMealsFlow }
    ) { cardios, report, notes, cheatMeals ->
        ReportState.Content(
            date = date,
            dailyReport = report,
            cardios = cardios,
            notes = notes,
            cheatMeals = cheatMeals
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
                    Field.ProteinGrams -> state.dailyReport.copy(proteinGrams = it.value)
                    Field.CheatMeal -> state.dailyReport.copy(meal = it.value)
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

        on(ReportEvent.AddCardio::class) {
            onState<ReportState.Content> { state ->
                addCardio.execute(AddCardio.Params(
                    date = date.toDate(),
                    type = "",
                    minutes = ""
                )).fold(
                    onSuccess = {},
                    onFailure = { addError(it) }
                )
            }
        }

        on(ReportEvent.UpdateCardio::class) {
            val cardio = when(it.cardioField) {
                CardioField.Type -> it.cardio.copy(type = it.value)
                CardioField.Minutes -> it.cardio.copy(minutes = it.value)
            }

            updateCardio.execute(UpdateCardio.Params(cardio)).fold(
                onSuccess = {},
                onFailure = { addError(it) }
            )
        }

        on(ReportEvent.DeleteCardio::class) {
            deleteCardio.execute(DeleteCardio.Params(it.cardio)).fold(
                onSuccess = {},
                onFailure = { addError(it) }
            )
        }


        on(ReportEvent.AddCheatMeal::class) {
            addCheatMeal.execute(AddCheatMeal.Params(date = date.toDate(), meal = "")).fold(
                onSuccess = {},
                onFailure = { addError(it) }
            )
        }

        on(ReportEvent.UpdateCheatMeal::class) {
            val updatedCheatMeal = it.meal.copy(meal = it.value)
            updateCheatMeal.execute(UpdateCheatMeal.Params(updatedCheatMeal)).fold(
                onSuccess = {},
                onFailure = { addError(it) }
            )
        }

        on(ReportEvent.DeleteCheatMeal::class) {
            deleteCheatMeal.execute(DeleteCheatMeal.Params(it.meal)).fold(
                onSuccess = {},
                onFailure = { addError(it) }
            )
        }

        on(ReportEvent.AddNote::class) {
            addNote.execute(AddNote.Params(date = date.toDate(), note = "")).fold(
                onSuccess = {},
                onFailure = { addError(it) }
            )
        }

        on(ReportEvent.UpdateNote::class) {
            val updatedNote = it.note.copy(note = it.value)
            updateNote.execute(UpdateNote.Params(updatedNote)).fold(
                onSuccess = {},
                onFailure = { addError(it) }
            )
        }

        on(ReportEvent.DeleteNote::class) {
            deleteNote.execute(DeleteNote.Params(it.note)).fold(
                onSuccess = {},
                onFailure = { addError(it) }
            )
        }

    }

    private suspend fun initDailyReport() {
        initDailyReport.execute(InitDailyReport.Params(date)).fold(
            onSuccess = {}, onFailure = { addError(it) }
        )
    }

    private suspend fun initCardio() {
        initCardio.execute(InitCardio.Params(date)).fold(
            onSuccess = {}, onFailure = { addError(it) }
        )
    }

    private suspend fun initCheatMeal() {
        initCheatMeal.execute(InitCheatMeal.Params(date)).fold(
            onSuccess = {}, onFailure = { addError(it) }
        )
    }

    private suspend fun initNote() {
        initNote.execute(InitNote.Params(date)).fold(
            onSuccess = {}, onFailure = { addError(it) }
        )
    }

}


sealed interface ReportEvent {
    object DeleteReport: ReportEvent
    object UpdateReport: ReportEvent

    object AddCardio: ReportEvent
    data class DeleteCardio(val cardio: CardioDomainEntity): ReportEvent
    data class UpdateCardio(
        val cardio: CardioDomainEntity,
        val cardioField: CardioField,
        val value: String
    ): ReportEvent

    data class UpdateCheckBox(val isChecked: Boolean, val checkBoxField: CheckBoxField): ReportEvent
    data class UpdateField(val value: String, val field: Field): ReportEvent
    data class SelectMuscle(val muscle: String): ReportEvent

    data class DeleteNote(val note: NoteDomainEntity): ReportEvent
    data class UpdateNote(val note: NoteDomainEntity, val value: String): ReportEvent
    object AddNote: ReportEvent

    object AddCheatMeal: ReportEvent
    data class DeleteCheatMeal(val meal: CheatMealDomainEntity): ReportEvent
    data class UpdateCheatMeal(val meal: CheatMealDomainEntity, val value: String): ReportEvent
}


sealed interface ReportState {

    object Idle: ReportState

    data class Content(
        val date: Long,
        val dailyReport: DailyReportDomainEntity,
        val cardios: List<CardioDomainEntity>,
        val notes: List<NoteDomainEntity>,
        val cheatMeals: List<CheatMealDomainEntity>
    ): ReportState

}


enum class CardioField {
    Type,
    Minutes
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
    ProteinGrams,
    CheatMeal,
}

data class CardioItem(
    val type: String,
    val minutes: String
)