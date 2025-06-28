package nondas.pap.fitness_routine.presentation.ui.screen.report

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
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
import kotlinx.coroutines.runBlocking
import nondas.pap.fitness_routine.InlineClassesAnswer
import nondas.pap.fitness_routine.cardio
import nondas.pap.fitness_routine.domain.entity.CardioDomainEntity
import nondas.pap.fitness_routine.domain.entity.CheatMealDomainEntity
import nondas.pap.fitness_routine.domain.entity.NoteDomainEntity
import nondas.pap.fitness_routine.domain.entity.enums.Muscle
import nondas.pap.fitness_routine.domain.interactor.settings.ChangeChoice

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


    private val reportFlow: MutableStateFlow<Result<DailyReportDomainEntity>> =
        MutableStateFlow(Result.success(report))

    private val cardiosFlow: MutableStateFlow<Result<List<CardioDomainEntity>>> =
        MutableStateFlow(Result.success(cardios))


    private val notesFlow: MutableStateFlow<Result<List<NoteDomainEntity>>> =
        MutableStateFlow(Result.success(notes))

    private val cheatMealsFlow: MutableStateFlow<Result<List<CheatMealDomainEntity>>> =
        MutableStateFlow(Result.success(cheatMeals))


    @Before
    fun setUp() = runTest {
        whenever(getDailyReport.execute(any())).thenReturn(reportFlow)

        whenever(getNotes.execute(any())).thenReturn(notesFlow)

        whenever(getCardios.execute(any())).thenReturn(cardiosFlow)

        whenever(getCheatMeals.execute(any())).thenReturn(cheatMealsFlow)

        whenever(updateReport.execute(any())).thenAnswer {
            InlineClassesAnswer { invocation ->
                val params = invocation.getArgument<UpdateDailyReport.Params>(0)
                val report = reportFlow.value.getOrThrow()


                val newReport = report.copy(
                    proteinGrams = params.report.proteinGrams,
                    date = params.report.date,
                    hadCreatine = params.report.hadCreatine,
                    sleepQuality = params.report.sleepQuality,
                    litersOfWater = params.report.litersOfWater,
                    musclesTrained = params.report.musclesTrained,
                    performedWorkout = params.report.performedWorkout,
                    hadCheatMeal = params.report.hadCheatMeal
                )

                emitReport(params.report)

                Result.success(Unit)
            }
        }

        whenever(addCardio.execute(any())).thenAnswer {
            InlineClassesAnswer { invocation ->
                val params = invocation.getArgument<AddCardio.Params>(0)
                val cardios = cardiosFlow.value.getOrThrow()

                val newCardio = CardioDomainEntity(
                    id = "",
                    date = params.date,
                    type = params.type,
                    minutes = params.minutes
                )

                emitCardios(cardios.plus(newCardio))

                Result.success(Unit)
            }
        }

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
                ),
                collectedStates
            )
        }
    }

//
//    @Test
//    fun onSelectMuscle_selectsMuscleGroup() = runTest {
//        initViewModel()
//
//        onEvents(
//            viewModel,
//            ReportEvent.SelectMuscle(Muscle.Shoulders)
//        ) { collectedStates ->
//
//            assertEquals(
//                listOf(
//                    defaultContent,
//                    defaultContent.copy(
//                        dailyReport = defaultContent.dailyReport.copy(
//                            musclesTrained = listOf(
//                                Muscle.Shoulders
//                            )
//                        )
//                    ),
//                ),
//                collectedStates
//            )
//        }
//    }
//
//
//    @Test
//    fun onAddCardio_addsNewCardio() = runTest {
//        initViewModel()
//
//        onEvents(
//            viewModel,
//            ReportEvent.AddCardio
//        ) { collectedStates ->
//            assertEquals(
//                listOf(
//                    defaultContent,
//                    defaultContent.copy(
//                        dailyReport = defaultContent.dailyReport.copy(
//                            musclesTrained = listOf(
//                                Muscle.Shoulders
//                            )
//                        )
//                    ),
//                ),
//                collectedStates
//            )
//        }
//    }

    private fun emitReport(report: DailyReportDomainEntity) = runBlocking {
        reportFlow.emit(Result.success(report))
    }

    private fun emitCardios(cardios: List<CardioDomainEntity>) = runBlocking {
        cardiosFlow.emit(Result.success(cardios))
    }


    private fun emitNotes(notes: List<NoteDomainEntity>) = runBlocking {
        notesFlow.emit(Result.success(notes))
    }


    private fun emitCheatMeals(meals: List<CheatMealDomainEntity>) = runBlocking {
        cheatMealsFlow.emit(Result.success(meals))
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
        val cardios = listOf<CardioDomainEntity>()
        val notes = listOf<NoteDomainEntity>()
        val cheatMeals = listOf<CheatMealDomainEntity>()

        private val defaultContent = ReportState.Content(
            date = date,
            dailyReport = report,
            cheatMeals = cheatMeals,
            notes = notes,
            cardios = cardios
        )

    }


}

