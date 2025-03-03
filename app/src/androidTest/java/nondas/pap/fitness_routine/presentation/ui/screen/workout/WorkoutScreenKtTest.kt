package nondas.pap.fitness_routine.presentation.ui.screen.workout

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import nondas.pap.fitness_routine.DummyEntities
import nondas.pap.fitness_routine.dailyReport
import nondas.pap.fitness_routine.domain.entity.SettingsDomainEntity
import nondas.pap.fitness_routine.domain.entity.enums.Choice
import nondas.pap.fitness_routine.domain.entity.enums.Muscle
import nondas.pap.fitness_routine.exercise
import nondas.pap.fitness_routine.presentation.component.AppSurface
import nondas.pap.fitness_routine.presentation.ui.screen.report.ReportState
import nondas.pap.fitness_routine.presentation.ui.screen.settings.SettingsScreen
import nondas.pap.fitness_routine.presentation.ui.screen.settings.SettingsState
import nondas.pap.fitness_routine.presentation.ui.screen.settings.SettingsViewModel
import nondas.pap.fitness_routine.set
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import nondas.pap.fitness_routine.presentation.util.getCurrentDate
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(AndroidJUnit4::class)
class WorkoutScreenKtTest {


    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var viewModel: WorkoutViewModel

    @Before
    fun setUp() {
        whenever(viewModel.errorFlow).thenReturn(MutableSharedFlow())
        whenever(viewModel.navigateToExercisesFlow).thenReturn(MutableSharedFlow())
    }


    @Test
    fun onContentState_whenMuscleIsClicked_addsSelectMuscle() {
        // given
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(defaultContent))

        composeTestRule.setContent {
            AppSurface {
                WorkoutScreen(viewModel = viewModel, navigateBack = {}, onNavigateToExercises = {}, onNavigateToScreen = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(WorkoutScreenConstants.MUSCLE+Muscle.Chest).performClick()

        // then
        verify(viewModel).add(WorkoutEvent.SelectMuscle(Muscle.Chest))

    }

    @Test
    fun onContentState_whenMuscleIsClicked_MuscleIsDisplayed() {
        // given
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(defaultContent))

        composeTestRule.setContent {
            AppSurface {
                WorkoutScreen(viewModel = viewModel, navigateBack = {}, onNavigateToExercises = {}, onNavigateToScreen = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(WorkoutScreenConstants.MUSCLE+Muscle.Chest).performClick()

        // then
        composeTestRule.onNodeWithTag(WorkoutScreenConstants.MUSCLE_TEXT+Muscle.Chest)
    }


    @Test
    fun onContentState_whenBreakButtonIsClicked_BreakDialogIsDisplayed() {
        // given
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(defaultContent.copy(dialog = Dialog.Break)))

        composeTestRule.setContent {
            AppSurface {
                WorkoutScreen(viewModel = viewModel, navigateBack = {}, onNavigateToExercises = {}, onNavigateToScreen = {})
            }
        }

        // then
        composeTestRule.onNodeWithTag(WorkoutScreenConstants.BREAK_DIALOG, useUnmergedTree = true).assertExists()
    }

    @Test
    fun onContentState_whenAddExerciseButtonIsClicked_showsAddExerciseDialog() {
        // given
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(
            defaultContent.copy(musclesTrained = listOf(Muscle.Chest), dialog = Dialog.AddExercise(Muscle.Chest)))
        )

        composeTestRule.setContent {
            AppSurface {
                WorkoutScreen(viewModel = viewModel, navigateBack = {}, onNavigateToExercises = {}, onNavigateToScreen = {})
            }
        }


        // then
        composeTestRule.onNodeWithTag(WorkoutScreenConstants.EXERCISE_DIALOG).assertIsDisplayed()

    }

    @Test
    fun onContentState_whenAddSetButtonIsClicked_addsSet() {
        // given
        val selectedMuscle = Muscle.Chest
        val selectedExercise = exercises.first { it.muscle == selectedMuscle }.name

        whenever(viewModel.uiState).thenReturn(MutableStateFlow(
            defaultContent.copy(
                musclesTrained = listOf(selectedMuscle),
                sets = listOf(set)
            )
        ))

        composeTestRule.setContent {
            AppSurface {
                WorkoutScreen(viewModel = viewModel, navigateBack = {}, onNavigateToExercises = {}, onNavigateToScreen = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(WorkoutScreenConstants.ADD_SET+selectedMuscle).performClick()

        // then
        verify(viewModel).add(WorkoutEvent.AddNewSet(muscle = selectedMuscle, exercise = selectedExercise))
    }


    @Test
    fun onContentState_whenDeleteButtonIsClicked_addsDeleteSet() {
        // given
        val selectedMuscle = Muscle.Chest
        val selectedExercise = exercises.first { it.muscle == selectedMuscle }.name

        whenever(viewModel.uiState).thenReturn(MutableStateFlow(
            defaultContent.copy(
                musclesTrained = listOf(selectedMuscle),
                sets = listOf(set),

            ))
        )

        composeTestRule.setContent {
            AppSurface {
                WorkoutScreen(viewModel = viewModel, navigateBack = {}, onNavigateToExercises = {}, onNavigateToScreen = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(WorkoutScreenConstants.DELETE_BUTTON+set.muscle+set.id)
            .performClick()

        // then
        verify(viewModel).add(WorkoutEvent.DeleteSet(set))
    }


    @Test
    fun onContentState_whenWeightTextFieldIsEdited_addsUpdateSet() {
        // given
        val selectedMuscle = Muscle.Chest
        val selectedExercise = exercises.first { it.muscle == selectedMuscle }.name

        whenever(viewModel.uiState).thenReturn(MutableStateFlow(
            defaultContent.copy(
                musclesTrained = listOf(selectedMuscle),
                sets = listOf(set),

                ))
        )

        composeTestRule.setContent {
            AppSurface {
                WorkoutScreen(viewModel = viewModel, navigateBack = {}, onNavigateToExercises = {}, onNavigateToScreen = {})
            }
        }

        // when
        val weight = "60"
        composeTestRule.onNodeWithTag(WorkoutScreenConstants.WEIGHT_TEXT_FIELD+set.id)
            .performTextInput(weight)


        // then
        verify(viewModel).add(WorkoutEvent.UpdateSet(
            set = set,
            field = SetField.Weight,
            value = weight
        ))
    }

    @Test
    fun onContentState_whenRepeatsTextFieldEdited_addsUpdateSet() {
        // given
        val selectedMuscle = Muscle.Chest
        val selectedExercise = exercises.first { it.muscle == selectedMuscle }.name

        whenever(viewModel.uiState).thenReturn(MutableStateFlow(
            defaultContent.copy(
                musclesTrained = listOf(selectedMuscle),
                sets = listOf(set))
        )
        )

        composeTestRule.setContent {
            AppSurface {
                WorkoutScreen(viewModel = viewModel, navigateBack = {}, onNavigateToExercises = {}, onNavigateToScreen = {})
            }
        }

        // when
        val repeats = "10"
        composeTestRule.onNodeWithTag(WorkoutScreenConstants.REPEATS_TEXT_FIELD+set.id)
            .performTextInput(repeats)


        // then
        verify(viewModel).add(WorkoutEvent.UpdateSet(
            set = set,
            field = SetField.Repeat,
            value = repeats
        ))
    }



    companion object {
        private val exercises = listOf(DummyEntities.exercise.copy(muscle = Muscle.Chest, name = "bench press"))
        private val set = DummyEntities.set.copy(exercise = exercises.first().name)
        val defaultContent = WorkoutState.Content(
            date = getCurrentDate(),
            sets = listOf(),
            exercises = exercises,
            musclesTrained = listOf(Muscle.Chest),
            dailyReport = DummyEntities.dailyReport.copy(musclesTrained = listOf(Muscle.Chest)),
            dialog = null,
            breakTimeDuration = "60"
        )
    }

}