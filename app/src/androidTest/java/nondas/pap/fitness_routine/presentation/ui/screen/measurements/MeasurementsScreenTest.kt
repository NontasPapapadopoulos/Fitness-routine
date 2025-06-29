package nondas.pap.fitness_routine.presentation.ui.screen.measurements

import androidx.compose.ui.test.junit4.createComposeRule
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
import org.mockito.kotlin.whenever

//@RunWith(MockitoJUnitRunner::class)
//class MeasurementsScreenTest {

//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    @get:Rule
//    val mockitoRule: MockitoRule = MockitoJUnit.rule()
//
//
//    @Mock
//    private lateinit var viewModel: BodyMeasurementViewModel
//
//
//    @Before
//    fun setUp() {
//        whenever(viewModel.errorFlow).thenReturn(MutableSharedFlow())
//    }
//
//    @Test
//    fun contentState_whenUpdatingField_addsUpdateField() {
//        // given
//        whenever(viewModel.uiState).thenReturn(MutableStateFlow(content))
//
//        composeTestRule.setContent {
//            AppSurface {
//                BodyMeasurementScreen(viewModel = viewModel, navigateBack = {})
//            }
//        }
//
//        // when
//


//    }




//    companion object {
//        val content = MeasurementState.Content(
//            hasBodyMeasurement = false,
//            bodyMeasurement = DummyEntities.bodyMeasurement
//        )
//    }

//}