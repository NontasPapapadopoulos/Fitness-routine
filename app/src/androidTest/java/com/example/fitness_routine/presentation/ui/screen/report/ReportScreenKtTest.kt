package com.example.fitness_routine.presentation.ui.screen.report

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.presentation.component.AppSurface
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
import java.util.Date

@RunWith(AndroidJUnit4::class)
class ReportScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()


    @Mock
    private lateinit var viewModel: ReportViewModel


    @Before
    fun setUp() {
        whenever(viewModel.errorFlow).thenReturn(MutableSharedFlow())
    }


    @Test
    fun contentState_whenCheckPerformWorkoutCheckBox_addsUpdateCheckBox() {
        // given
        whenever(viewModel.uiState).thenReturn(
            MutableStateFlow(ReportState.Content(date = 0, dailyReport = report))
        )

        composeTestRule.setContent {
            AppSurface {
                ReportScreen(viewModel = viewModel, navigateToWorkout = {}, navigateBack = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(ReportScreenConstants.WORKOUT_CHECK_BOX).performClick()

        // then
        verify(viewModel).add(ReportEvent.UpdateCheckBox(false, CheckBoxField.Workout))
    }

    @Test
    fun contentState_whenCheckHadCreatineCheckBox_addsUpdateCheckBox() {
        // given
        whenever(viewModel.uiState).thenReturn(
            MutableStateFlow(ReportState.Content(date = 0, dailyReport = report))
        )

        composeTestRule.setContent {
            AppSurface {
                ReportScreen(viewModel = viewModel, navigateToWorkout = {}, navigateBack = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(ReportScreenConstants.CREATINE_CHECK_BOX).performClick()

        // then
        verify(viewModel).add(ReportEvent.UpdateCheckBox(false, CheckBoxField.Creatine))
    }

    @Test
    fun contentState_whenCheckHadCheatMealCheckBox_addsUpdateCheckBox() {
        // given
        whenever(viewModel.uiState).thenReturn(
            MutableStateFlow(ReportState.Content(date = 0, dailyReport = report))
        )

        composeTestRule.setContent {
            AppSurface {
                ReportScreen(viewModel = viewModel, navigateToWorkout = {}, navigateBack = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(ReportScreenConstants.CHEAT_MEAL_CHECK_BOX).performClick()

        // then
        verify(viewModel).add(ReportEvent.UpdateCheckBox(false, CheckBoxField.CheatMeal))
    }


    @Test
    fun contentState_whenCardioMinutesTextFieldIsEntered_addsUpdateField() {
        // given
        whenever(viewModel.uiState).thenReturn(
            MutableStateFlow(ReportState.Content(date = 0, dailyReport = report))
        )

        composeTestRule.setContent {
            AppSurface {
                ReportScreen(viewModel = viewModel, navigateToWorkout = {}, navigateBack = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(ReportScreenConstants.CARDIO_TEXT_FIELD).performTextInput("input")

        // then
        verify(viewModel).add(ReportEvent.UpdateField("input", Field.CardioMinutes))
    }


    @Test
    fun contentState_whenLitersOfWaterTextFieldIsEntered_addsUpdateField() {
        // given
        whenever(viewModel.uiState).thenReturn(
            MutableStateFlow(ReportState.Content(date = 0, dailyReport = report))
        )

        composeTestRule.setContent {
            AppSurface {
                ReportScreen(viewModel = viewModel, navigateToWorkout = {}, navigateBack = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(ReportScreenConstants.WATER_TEXT_FIELD).performTextInput("input")

        // then
        verify(viewModel).add(ReportEvent.UpdateField("input", Field.LitersOfWater))
    }

    @Test
    fun contentState_whenProteinGramsTextFieldIsEntered_addsUpdateField() {
        // given
        whenever(viewModel.uiState).thenReturn(
            MutableStateFlow(ReportState.Content(date = 0, dailyReport = report))
        )

        composeTestRule.setContent {
            AppSurface {
                ReportScreen(viewModel = viewModel, navigateToWorkout = {}, navigateBack = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(ReportScreenConstants.PROTEIN_TEXT_FIELD).performTextInput("input")

        // then
        verify(viewModel).add(ReportEvent.UpdateField("input", Field.ProteinGrams))
    }

    @Test
    fun contentState_whenSleepQualityIsClicked_addsUpdateField() {
        // given
        whenever(viewModel.uiState).thenReturn(
            MutableStateFlow(ReportState.Content(date = 0, dailyReport = report))
        )

        composeTestRule.setContent {
            AppSurface {
                ReportScreen(viewModel = viewModel, navigateToWorkout = {}, navigateBack = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(ReportScreenConstants.SLEEP_QUALITY).performTextInput("5")

        // then
        verify(viewModel).add(ReportEvent.UpdateField("5", Field.SleepQuality))
    }

    @Test
    fun contentState_whenGymNotesTextFieldIsEntered_addsUpdateField() {
        // given
        whenever(viewModel.uiState).thenReturn(
            MutableStateFlow(ReportState.Content(date = 0, dailyReport = report))
        )

        composeTestRule.setContent {
            AppSurface {
                ReportScreen(viewModel = viewModel, navigateToWorkout = {}, navigateBack = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(ReportScreenConstants.GYM_NOTES_TEXT_FIELD).performTextInput("input")

        // then
        verify(viewModel).add(ReportEvent.UpdateField("input", Field.GymNotes))
    }

    @Test
    fun contentState_whenMuscleIsClicked_addsUpdateField() {
        // given
        whenever(viewModel.uiState).thenReturn(
            MutableStateFlow(ReportState.Content(date = 0, dailyReport = report))
        )

        composeTestRule.setContent {
            AppSurface {
                ReportScreen(viewModel = viewModel, navigateToWorkout = {}, navigateBack = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(ReportScreenConstants.MUSCLE_ITEM + Muscle.Chest.name).performClick()

        // then
        verify(viewModel).add(ReportEvent.UpdateField(Muscle.Chest.name, Field.TrainedMuscles))
    }


    @Test
    fun contentState_whenClickDeleteButton_addsDeleteReport() {
        // given
        whenever(viewModel.uiState).thenReturn(
            MutableStateFlow(ReportState.Content(date = 0, dailyReport = report))
        )

        composeTestRule.setContent {
            AppSurface {
                ReportScreen(viewModel = viewModel, navigateToWorkout = {}, navigateBack = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(ReportScreenConstants.DELETE_BUTTON).performClick()

        verify(viewModel).add(ReportEvent.DeleteReport)
    }




    companion object {
        val report = DailyReportDomainEntity(
            date = Date(),
            performedWorkout = false,
            hadCreatine = false,
            hadCheatMeal = false,
            gymNotes = "",
            litersOfWater = "",
            proteinGrams = "",
            cardioMinutes = "",
            sleepQuality = "1",
            musclesTrained = listOf()
        )
    }
}