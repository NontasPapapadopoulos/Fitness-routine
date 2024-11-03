package com.example.fitness_routine.presentation.ui.screen.exercise

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.fitness_routine.domain.entity.ExerciseDomainEntity
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.presentation.component.AppSurface
import com.example.fitness_routine.presentation.ui.screen.report.ReportEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
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
    fun contentState_whenClickAddExerciseButton_addsAddExercise() {
        // given
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(content))
        
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
        composeTestRule.onNodeWithTag(ExerciseScreenConstants.MUSCLE_GROUP_DROPDOWN).performClick()
        composeTestRule.onNodeWithTag(ExerciseScreenConstants.DELETE_EXERCISE + "bench press").performClick()
    }

    
    
    companion object {
        val selectedMuscle = Muscle.Chest
        val content = ExerciseState.Content(
            preSelectedMuscle = selectedMuscle,
            exercises = listOf(ExerciseDomainEntity(name = "bench press", muscle = Muscle.Chest)), 
            newExercise = ""
        )
    }

}