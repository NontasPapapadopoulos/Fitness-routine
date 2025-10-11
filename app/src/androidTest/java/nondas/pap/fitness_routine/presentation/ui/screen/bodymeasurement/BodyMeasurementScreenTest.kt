package nondas.pap.fitness_routine.presentation.ui.screen.bodymeasurement

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import nondas.pap.fitness_routine.DummyEntities
import nondas.pap.fitness_routine.bodyMeasurement
import nondas.pap.fitness_routine.presentation.component.AppSurface
import nondas.pap.fitness_routine.presentation.ui.screen.bodymeasurement.BodyMeasurementScreen
import nondas.pap.fitness_routine.presentation.ui.screen.bodymeasurement.BodyMeasurementViewModel
import nondas.pap.fitness_routine.presentation.ui.screen.bodymeasurement.MeasurementState
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
class BodyMeasurementScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()


    @Mock
    private lateinit var viewModel: BodyMeasurementViewModel


    @Before
    fun setUp() {
        whenever(viewModel.errorFlow).thenReturn(MutableSharedFlow())
    }

    @Test
    fun contentState_whenUpdatingWeight_addsUpdateField() {
        // given
        val input = "80"
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(content))

        composeTestRule.setContent {
            AppSurface {
                BodyMeasurementScreen(viewModel = viewModel, navigateBack = {}, date = 0L)
            }
        }

        // when
        composeTestRule.onNodeWithTag(MeasurementsScreenConstants.WEIGHT_TEXT_FIELD)
            .performTextInput(input)

        // then
        verify(viewModel).add(MeasurementEvent.UpdateField(MeasurementField.Weight, input))

    }

    @Test
    fun contentState_whenUpdatingFat_addsUpdateField() {
        // given
        val input = "12"
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(content))

        composeTestRule.setContent {
            AppSurface {
                BodyMeasurementScreen(viewModel = viewModel, navigateBack = {}, date = 0L)
            }
        }

        // when
        composeTestRule.onNodeWithTag(MeasurementsScreenConstants.FAT_TEXT_FIELD)
            .performTextInput(input)

        // then
        verify(viewModel).add(MeasurementEvent.UpdateField(MeasurementField.Fat, input))

    }

    @Test
    fun contentState_whenUpdatingMuscleMass_addsUpdateField() {
        // given
        val input = "12"
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(content))

        composeTestRule.setContent {
            AppSurface {
                BodyMeasurementScreen(viewModel = viewModel, navigateBack = {}, date = 0L)
            }
        }

        // when
        composeTestRule.onNodeWithTag(MeasurementsScreenConstants.MUSCLE_MASS_TEXT_FIELD)
            .performTextInput(input)

        // then
        verify(viewModel).add(MeasurementEvent.UpdateField(MeasurementField.MuscleMass, input))

    }

    @Test
    fun contentState_whenUpdatingBMI_addsUpdateField() {
        // given
        val input = "12"
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(content))

        composeTestRule.setContent {
            AppSurface {
                BodyMeasurementScreen(viewModel = viewModel, navigateBack = {}, date = 0L)
            }
        }

        // when
        composeTestRule.onNodeWithTag(MeasurementsScreenConstants.BMI_TEXT_FIELD)
            .performTextInput(input)

        // then
        verify(viewModel).add(MeasurementEvent.UpdateField(MeasurementField.BMI, input))

    }


    @Test
    fun contentState_whenUpdatingTBW_addsUpdateField() {
        // given
        val input = "12"
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(content))

        composeTestRule.setContent {
            AppSurface {
                BodyMeasurementScreen(viewModel = viewModel, navigateBack = {}, date = 0L)
            }
        }

        // when
        composeTestRule.onNodeWithTag(MeasurementsScreenConstants.TBW_TEXT_FIELD)
            .performTextInput(input)

        // then
        verify(viewModel).add(MeasurementEvent.UpdateField(MeasurementField.TBW, input))

    }

    @Test
    fun contentState_whenUpdatingBMR_addsUpdateField() {
        // given
        val input = "12"
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(content))

        composeTestRule.setContent {
            AppSurface {
                BodyMeasurementScreen(viewModel = viewModel, navigateBack = {}, date = 0L)
            }
        }

        // when
        composeTestRule.onNodeWithTag(MeasurementsScreenConstants.BMR_TEXT_FIELD)
            .performTextInput(input)

        // then
        verify(viewModel).add(MeasurementEvent.UpdateField(MeasurementField.BMR, input))

    }

    @Test
    fun contentState_whenUpdatingVisceralFat_addsUpdateField() {
        // given
        val input = "12"
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(content))

        composeTestRule.setContent {
            AppSurface {
                BodyMeasurementScreen(viewModel = viewModel, navigateBack = {}, date = 0L)
            }
        }

        // when
        composeTestRule.onNodeWithTag(MeasurementsScreenConstants.VISCERAL_FAT_TEXT_FIELD)
            .performTextInput(input)

        // then
        verify(viewModel).add(MeasurementEvent.UpdateField(MeasurementField.VisceralFat, input))

    }

    @Test
    fun contentState_whenUpdatingMetabolicAge_addsUpdateField() {
        // given
        val input = "12"
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(content))

        composeTestRule.setContent {
            AppSurface {
                BodyMeasurementScreen(viewModel = viewModel, navigateBack = {}, date = 0L)
            }
        }

        // when
        composeTestRule.onNodeWithTag(MeasurementsScreenConstants.METABOLIC_AGE_TEXT_FIELD)
            .performTextInput(input)

        // then
        verify(viewModel).add(MeasurementEvent.UpdateField(MeasurementField.MetabolicAge, input))

    }


    @Test
    fun onContentState_whenClickAddMeasurementButton_addsAddMeasurement() {
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(content))

        composeTestRule.setContent {
            AppSurface {
                BodyMeasurementScreen(viewModel = viewModel, navigateBack = {}, date = 0L)
            }
        }

        // when
        composeTestRule.onNodeWithTag(MeasurementsScreenConstants.BUTTON)
            .performClick()

        // then
        verify(viewModel).add(MeasurementEvent.Add)
    }


    @Test
    fun onContentState_whenClickAddMeasurementButton_addsUpdateMeasurement() {
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(content.copy(hasBodyMeasurement = true)))

        composeTestRule.setContent {
            AppSurface {
                BodyMeasurementScreen(viewModel = viewModel, navigateBack = {}, date = 0L)
            }
        }

        // when
        composeTestRule.onNodeWithTag(MeasurementsScreenConstants.BUTTON)
            .performClick()

        // then
        verify(viewModel).add(MeasurementEvent.Update)
    }

    companion object {
        val content = MeasurementState.Content(
            hasBodyMeasurement = false,
            bodyMeasurement = DummyEntities.bodyMeasurement
        )
    }

}