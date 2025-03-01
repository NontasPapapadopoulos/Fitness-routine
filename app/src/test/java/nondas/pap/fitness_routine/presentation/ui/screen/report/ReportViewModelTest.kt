package nondas.pap.fitness_routine.presentation.ui.screen.report

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import nondas.pap.fitness_routine.DummyEntities
import nondas.pap.fitness_routine.dailyReport
import nondas.pap.fitness_routine.domain.entity.DailyReportDomainEntity
import nondas.pap.fitness_routine.domain.interactor.cardio.AddCardio
import nondas.pap.fitness_routine.domain.interactor.cardio.DeleteCardio
import nondas.pap.fitness_routine.domain.interactor.cardio.GetCardios
import nondas.pap.fitness_routine.domain.interactor.cardio.InitCardio
import nondas.pap.fitness_routine.domain.interactor.cardio.UpdateCardio
import nondas.pap.fitness_routine.domain.interactor.cheat.AddCheatMeal
import nondas.pap.fitness_routine.domain.interactor.cheat.DeleteCheatMeal
import nondas.pap.fitness_routine.domain.interactor.cheat.GetCheatMeals
import nondas.pap.fitness_routine.domain.interactor.cheat.InitCheatMeal
import nondas.pap.fitness_routine.domain.interactor.cheat.UpdateCheatMeal
import nondas.pap.fitness_routine.domain.interactor.note.AddNote
import nondas.pap.fitness_routine.domain.interactor.note.DeleteNote
import nondas.pap.fitness_routine.domain.interactor.note.GetNotes
import nondas.pap.fitness_routine.domain.interactor.note.InitNote
import nondas.pap.fitness_routine.domain.interactor.note.UpdateNote
import nondas.pap.fitness_routine.domain.interactor.report.AddDailyReport
import nondas.pap.fitness_routine.domain.interactor.report.DeleteDailyReport
import nondas.pap.fitness_routine.domain.interactor.report.GetDailyReport
import nondas.pap.fitness_routine.domain.interactor.report.InitDailyReport
import nondas.pap.fitness_routine.domain.interactor.report.UpdateDailyReport
import nondas.pap.fitness_routine.presentation.navigation.NavigationArgument
import nondas.pap.fitness_routine.presentation.ui.screen.MainDispatcherRule
import nondas.pap.fitness_routine.presentation.ui.screen.onEvents
import nondas.pap.fitness_routine.presentation.util.toTimeStamp
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import nondas.pap.fitness_routine.presentation.ui.screen.onEvents
import kotlinx.coroutines.flow.flow

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class ReportViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ReportViewModel

    @Mock
    private lateinit var getDailyReport: GetDailyReport

    @Mock
    private lateinit var deleteReport: DeleteDailyReport

    @Mock
    private lateinit var updateReport: UpdateDailyReport

    @Mock
    private lateinit var initDailyReport: InitDailyReport

    @Mock
    private lateinit var initCardio: InitCardio

    @Mock
    private lateinit var getCardios: GetCardios

    @Mock
    private lateinit var deleteCardio: DeleteCardio

    @Mock
    private lateinit var updateCardio: UpdateCardio

    @Mock
    private lateinit var addCardio: AddCardio

    @Mock
    private lateinit var getCheatMeals: GetCheatMeals

    @Mock
    private lateinit var getNotes: GetNotes

    @Mock
    private lateinit var initNote: InitNote

    @Mock
    private lateinit var initCheatMeal: InitCheatMeal

    @Mock
    private lateinit var addCheatMeal: AddCheatMeal

    @Mock
    private lateinit var deleteCheatMeal: DeleteCheatMeal

    @Mock
    private lateinit var updateCheatMeal: UpdateCheatMeal

    @Mock
    private lateinit var addNote: AddNote

    @Mock
    private lateinit var deleteNote: DeleteNote

    @Mock
    private lateinit var updateNote: UpdateNote

    private val savedStateHandle: SavedStateHandle =
        SavedStateHandle(mapOf(NavigationArgument.Date.param to date))

    @Before
    fun setUp() = runTest{
        whenever(getDailyReport.execute(any())).thenReturn(flowOf(Result.success(report)))
        whenever(updateReport.execute(any())).thenReturn(Result.success(Unit))
//        whenever(deleteReport.execute(any())).thenReturn(Result.success(Unit))
//        whenever(initDailyReport.execute(any())).thenReturn(Result.success(Unit))

        whenever(getNotes.execute(any())).thenReturn(flowOf(Result.success(listOf())))
//        whenever(initNote.execute(any())).thenReturn(Result.success(Unit))
//        whenever(updateNote.execute(any())).thenReturn(Result.success(Unit))
//        whenever(deleteNote.execute(any())).thenReturn(Result.success(Unit))
//        whenever(addNote.execute(any())).thenReturn(Result.success(Unit))

        whenever(getCardios.execute(any())).thenReturn(flowOf(Result.success(listOf())))
//        whenever(addCardio.execute(any())).thenReturn(Result.success(Unit))
//        whenever(deleteCardio.execute(any())).thenReturn(Result.success(Unit))
//        whenever(updateCardio.execute(any())).thenReturn(Result.success(Unit))
//        whenever(initCardio.execute(any())).thenReturn(Result.success(Unit))

        whenever(getCheatMeals.execute(any())).thenReturn(flowOf(Result.success(listOf())))
//        whenever(addCheatMeal.execute(any())).thenReturn(Result.success(Unit))
//        whenever(deleteCheatMeal.execute(any())).thenReturn(Result.success(Unit))
//        whenever(updateCheatMeal.execute(any())).thenReturn(Result.success(Unit))
//        whenever(initCheatMeal.execute(any())).thenReturn(Result.success(Unit))


    }

    @Test
    fun onFlowStart_loadDailyReport() = runTest {
        initViewModel()

        onEvents(viewModel) { collectedStates ->
            assertEquals(
                listOf(
                    ReportState.Idle,
                    defaultContent
                ),
                collectedStates
            )
        }
    }

    @Test
    fun onUpdateCheckBox_togglesCheckBox() = runTest {
//        whenever(getDailyReport.execute(any())).thenReturn(flowOf(Result.success(report.copy(
//            performedWorkout = true,
//            hadCreatine = true,
//            hadCheatMeal = true
//        ))))

        initViewModel()


        onEvents(
            viewModel,
            ReportEvent.UpdateCheckBox(isChecked = true, CheckBoxField.Workout),
        ) { collectedStates ->

            assertEquals(
                listOf(
                    ReportState.Idle,
                    defaultContent,
                    defaultContent.copy(dailyReport = report.copy(performedWorkout = !report.performedWorkout)),
//                    defaultContent.copy(dailyReport = report.copy(hadCreatine = !report.hadCreatine)),
//                    defaultContent.copy(dailyReport = report.copy(hadCheatMeal = !report.hadCheatMeal)),
                ),
                collectedStates
            )
        }
    }


    @Test
    fun onSelectMuscle_selectsMuscleGroups() = runTest {

    }


    private fun initViewModel() {
        viewModel = ReportViewModel(
            getDailyReport = getDailyReport,
            deleteReport = deleteReport,
            updateReport = updateReport,
            savedStateHandle = savedStateHandle,
            initDailyReport = initDailyReport,
            initCardio = initCardio,
            getCardios = getCardios,
            deleteCardio = deleteCardio,
            updateCardio = updateCardio,
            addCardio = addCardio,
            getCheatMeals = getCheatMeals,
            getNotes = getNotes,
            initNote = initNote,
            initCheatMeal = initCheatMeal,
            addCheatMeal = addCheatMeal,
            deleteCheatMeal = deleteCheatMeal,
            updateCheatMeal = updateCheatMeal,
            addNote = addNote,
            deleteNote = deleteNote,
            updateNote = updateNote
        )
    }


    companion object {
        val report = DummyEntities.dailyReport
        val date = Date().toTimeStamp()


        private val defaultContent = ReportState.Content(
            date = date,
            dailyReport = report,
            cheatMeals = listOf(),
            notes = listOf(),
            cardios = listOf()
        )

    }




}

