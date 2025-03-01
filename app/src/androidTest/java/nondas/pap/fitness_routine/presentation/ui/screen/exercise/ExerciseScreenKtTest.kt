package nondas.pap.fitness_routine.presentation.ui.screen.exercise

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import nondas.pap.fitness_routine.DummyEntities
import nondas.pap.fitness_routine.domain.entity.ExerciseDomainEntity
import nondas.pap.fitness_routine.domain.entity.enums.Muscle
import nondas.pap.fitness_routine.exercise
import nondas.pap.fitness_routine.presentation.component.AppSurface
import nondas.pap.fitness_routine.presentation.ui.screen.report.ReportEvent
import nondas.pap.fitness_routine.set
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class ExerciseScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()


    @Mock
    private lateinit var viewModel: ExerciseViewModel

    @Before
    fun setUp() {
        whenever(viewModel.errorFlow).thenReturn(MutableSharedFlow())
    }

    @Test
    fun contentState_whenClickUpdateButton_displaysEditExerciseDialog() {
        // given
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(content.copy(selectedExercise = selectedExercise)))

        composeTestRule.setContent {
            AppSurface {
                ExerciseScreen(navigateBack = {}, viewModel = viewModel)
            }
        }

        // then
        composeTestRule.onNodeWithTag(ExerciseScreenConstants.EDIT_DIALOG).assertIsDisplayed()
    }


    @Test
    fun contentState_whenClickAddExerciseButton_addsAddExercise() {
        // given
        val exercise = "new exercise"

        whenever(viewModel.uiState).thenReturn(MutableStateFlow(content.copy(newExercise = exercise)))
        
        composeTestRule.setContent { 
            AppSurface {
                ExerciseScreen(navigateBack = {}, viewModel = viewModel)
            }
        }

        // when
        composeTestRule.onNodeWithTag(ExerciseScreenConstants.ADD_EXERCISE_BUTTON).performClick()

        // then
        verify(viewModel).add(ExerciseEvent.Add(selectedMuscle))
    }

    @Test
    fun contentState_whenExerciseTextFieldIsEntered_addsTextChanged() {
        // given
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(content))

        composeTestRule.setContent {
            AppSurface {
                ExerciseScreen(navigateBack = {}, viewModel = viewModel)
            }
        }

        // when
        composeTestRule.onNodeWithTag(ExerciseScreenConstants.EXERCISE_TEXT_FIELD).performTextInput("pek dek")

        // then
        verify(viewModel).add(ExerciseEvent.TextChanged("pek dek"))
    }


    @Test
    fun contentState_whenDeleteButtonIsClicked_addsDelete() {
        // given
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(content))

        composeTestRule.setContent {
            AppSurface {
                ExerciseScreen(navigateBack = {}, viewModel = viewModel)
            }
        }

        // when
        val exercise = content.exercises.first()
        composeTestRule.onNodeWithTag(ExerciseScreenConstants.DELETE_EXERCISE + exercise.name).performClick()

        verify(viewModel).add(ExerciseEvent.Delete(exercise))

    }

    @Test
    fun contentState_whenEditButtonIsClicked_addsSelectExercise() {
        // given
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(content))

        composeTestRule.setContent {
            AppSurface {
                ExerciseScreen(navigateBack = {}, viewModel = viewModel)
            }
        }

        // when
        val exercise = content.exercises.first()
        composeTestRule.onNodeWithTag(ExerciseScreenConstants.EDIT_EXERCISE + exercise.name).performClick()

        verify(viewModel).add(ExerciseEvent.SelectExercise(exercise))

    }

    @Test
    fun contentState_EditDialogIsDisplayedAndTextFieldIsEntered_addsNewExerciseNameTextChanged() {
        // given
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(content.copy(selectedExercise = selectedExercise)))

        composeTestRule.setContent {
            AppSurface {
                ExerciseScreen(navigateBack = {}, viewModel = viewModel)
            }
        }

        // when
        val newName = "new_name"
        composeTestRule.onNodeWithTag(ExerciseScreenConstants.NEW_EXERCISE_NAME_TEXT_FIELD).performTextInput(newName)

        verify(viewModel).add(ExerciseEvent.NewExerciseNameTextChanged(newName))

    }

    @Test
    fun contentState_EditDialogIsDisplayedCancelButtonIsClicked_addsDismissDialog() {
        // given
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(content.copy(selectedExercise = selectedExercise)))

        composeTestRule.setContent {
            AppSurface {
                ExerciseScreen(navigateBack = {}, viewModel = viewModel)
            }
        }

        // when
        composeTestRule.onNodeWithTag(ExerciseScreenConstants.CANCEL_BUTTON).performClick()

        // then
        verify(viewModel).add(ExerciseEvent.DismissDialog)

    }

    @Test
    fun contentState_EditDialogIsDisplayedRenameButtonIsClicked_addsUpdateExercise() {
        // given
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(content.copy(selectedExercise = selectedExercise)))

        composeTestRule.setContent {
            AppSurface {
                ExerciseScreen(navigateBack = {}, viewModel = viewModel)
            }
        }

        // when
        composeTestRule.onNodeWithTag(ExerciseScreenConstants.RENAME_BUTTON).performClick()

        // then
        verify(viewModel).add(ExerciseEvent.UpdateExercise)

    }






    companion object {
        val selectedMuscle = Muscle.Chest
        val selectedExercise = DummyEntities.exercise.copy(name = "bench press", muscle = Muscle.Chest)
        val content = ExerciseState.Content(
            preSelectedMuscle = selectedMuscle,
            exercises = listOf(selectedExercise),
            newExercise = "",
            newName = "",
            selectedExercise = null
        )
    }

}