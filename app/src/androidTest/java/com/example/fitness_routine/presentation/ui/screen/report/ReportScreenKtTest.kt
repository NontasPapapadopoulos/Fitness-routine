package com.example.fitness_routine.presentation.ui.screen.report

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.fitness_routine.DummyEntities
import com.example.fitness_routine.cardio
import com.example.fitness_routine.cheatMeal
import com.example.fitness_routine.dailyReport
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.note
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
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(defaultContent))

        composeTestRule.setContent {
            AppSurface {
                ReportScreen(viewModel = viewModel, navigateToWorkout = {}, navigateBack = {}, navigateToBodyMeasurement = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(ReportScreenConstants.WORKOUT_CHECK_BOX).performClick()

        // then
        verify(viewModel).add(ReportEvent.UpdateCheckBox(!report.performedWorkout, CheckBoxField.Workout))
    }

    @Test
    fun contentState_whenCheckHadCreatineCheckBox_addsUpdateCheckBox() {
        // given
        whenever(viewModel.uiState).thenReturn(
            MutableStateFlow(defaultContent)
        )

        composeTestRule.setContent {
            AppSurface {
                ReportScreen(viewModel = viewModel, navigateToWorkout = {}, navigateBack = {}, navigateToBodyMeasurement = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(ReportScreenConstants.NUTRITION_TAB).performClick()
        composeTestRule.onNodeWithTag(ReportScreenConstants.CREATINE_CHECK_BOX).performClick()

        // then
        verify(viewModel).add(ReportEvent.UpdateCheckBox(!report.hadCreatine, CheckBoxField.Creatine))
    }

    @Test
    fun contentState_whenCheckHadCheatMealCheckBox_addsUpdateCheckBox() {
        // given
        whenever(viewModel.uiState).thenReturn(
            MutableStateFlow(defaultContent)
        )

        composeTestRule.setContent {
            AppSurface {
                ReportScreen(viewModel = viewModel, navigateToWorkout = {}, navigateBack = {}, navigateToBodyMeasurement = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(ReportScreenConstants.NUTRITION_TAB).performClick()
        composeTestRule.onNodeWithTag(ReportScreenConstants.CHEAT_MEAL_CHECK_BOX).performClick()

        // then
        verify(viewModel).add(ReportEvent.UpdateCheckBox(!report.hadCheatMeal, CheckBoxField.CheatMeal))
    }


    @Test
    fun contentState_whenCardioMinutesTextFieldIsEntered_addsUpdateCardio() {
        // given
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(defaultContent))

        composeTestRule.setContent {
            AppSurface {
                ReportScreen(viewModel = viewModel, navigateToWorkout = {}, navigateBack = {}, navigateToBodyMeasurement = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(ReportScreenConstants.CARDIO_TEXT_FIELD+"0").performTextInput("60")

        // then
        verify(viewModel).add(ReportEvent.UpdateCardio(value = "60", cardioField = CardioField.Minutes, cardio = defaultContent.cardios.first()))
    }


    @Test
    fun contentState_whenLitersOfWaterTextFieldIsEntered_addsUpdateField() {
        // given
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(defaultContent))

        composeTestRule.setContent {
            AppSurface {
                ReportScreen(viewModel = viewModel, navigateToWorkout = {}, navigateBack = {}, navigateToBodyMeasurement = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(ReportScreenConstants.NUTRITION_TAB).performClick()
        composeTestRule.onNodeWithTag(ReportScreenConstants.WATER_TEXT_FIELD).performTextInput("3")

        // then
        verify(viewModel).add(ReportEvent.UpdateField("3", Field.LitersOfWater))
    }

    @Test
    fun contentState_whenProteinGramsTextFieldIsEntered_addsUpdateField() {
        // given
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(defaultContent))

        composeTestRule.setContent {
            AppSurface {
                ReportScreen(viewModel = viewModel, navigateToWorkout = {}, navigateBack = {}, navigateToBodyMeasurement = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(ReportScreenConstants.NUTRITION_TAB).performClick()
        composeTestRule.onNodeWithTag(ReportScreenConstants.PROTEIN_TEXT_FIELD).performTextInput("111")

        // then
        verify(viewModel).add(ReportEvent.UpdateField("111", Field.ProteinGrams))
    }

    @Test
    fun contentState_whenSleepQualityIsClicked_addsUpdateField() {
        // given
        whenever(viewModel.uiState).thenReturn(
            MutableStateFlow(defaultContent))

        composeTestRule.setContent {
            AppSurface {
                ReportScreen(viewModel = viewModel, navigateToWorkout = {}, navigateBack = {}, navigateToBodyMeasurement = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(ReportScreenConstants.NUTRITION_TAB).performClick()
        composeTestRule.onNodeWithTag(ReportScreenConstants.SLEEP_QUALITY+3).performClick()

        // then
        verify(viewModel).add(ReportEvent.UpdateField("3", Field.SleepQuality))
    }

    @Test
    fun contentState_whenGymNotesTextFieldIsEntered_addsUpdateField() {
        // given
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(defaultContent))

        composeTestRule.setContent {
            AppSurface {
                ReportScreen(viewModel = viewModel, navigateToWorkout = {}, navigateBack = {}, navigateToBodyMeasurement = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(ReportScreenConstants.GYM_NOTES_TEXT_FIELD).performTextInput("input")

        // then
        verify(viewModel).add(ReportEvent.UpdateNote(value = "input", note = defaultContent.notes.first()))
    }

    @Test
    fun contentState_whenMuscleIsClicked_addsSelectField() {
        // given
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(defaultContent))

        composeTestRule.setContent {
            AppSurface {
                ReportScreen(viewModel = viewModel, navigateToWorkout = {}, navigateBack = {}, navigateToBodyMeasurement = {})
            }
        }

        // when
        composeTestRule.onNodeWithTag(ReportScreenConstants.MUSCLE_ITEM + Muscle.Chest.name).performClick()

        // then
        verify(viewModel).add(ReportEvent.SelectMuscle(Muscle.Chest.name, ))
    }



    companion object {
        val report = DummyEntities.dailyReport
        val cheatMeals = listOf(DummyEntities.cheatMeal)
        val cardios = listOf(DummyEntities.cardio)
        val notes = listOf(DummyEntities.note)

        val defaultContent = ReportState.Content(
            dailyReport = report,
            cheatMeals = cheatMeals,
            cardios = cardios,
            notes = notes,
            date = 1000L
        )
    }
}