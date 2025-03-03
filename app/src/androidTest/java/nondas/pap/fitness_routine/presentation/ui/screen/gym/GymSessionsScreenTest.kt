package nondas.pap.fitness_routine.presentation.ui.screen.gym

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import nondas.pap.fitness_routine.DummyEntities
import nondas.pap.fitness_routine.cardio
import nondas.pap.fitness_routine.dailyReport
import nondas.pap.fitness_routine.domain.entity.CardioDomainEntity
import nondas.pap.fitness_routine.domain.entity.enums.Cardio
import nondas.pap.fitness_routine.domain.entity.enums.Muscle
import nondas.pap.fitness_routine.presentation.component.AppSurface
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
class GymSessionsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var viewModel: GymSessionsViewModel


    @Before
    fun setUp() {
        whenever(viewModel.errorFlow).thenReturn(MutableSharedFlow())
    }


    @Test
    fun contentState_whenSelectMuscle_addsSelectMuscle() {
        // given
        whenever(viewModel.uiState).thenReturn(MutableStateFlow(content))

        composeTestRule.setContent {
            AppSurface {
                GymSessionsScreen(
                    viewModel = viewModel,
                    navigateToScreen = {},
                    navigateToWorkoutScreen = {},
                    navigateBack = {}
                )
            }
        }

        // when
        composeTestRule.onNodeWithTag(GymSessionsScreenConstants.MUSCLE_ITEM + Muscle.Chest.name)
            .performClick()


        // then
        verify(viewModel).add(GymSessionsEvent.SelectMuscle(Muscle.Chest))
    }




    companion object {
        val content = GymSessionsState.Content(
            selectedMuscles = listOf(),
            workoutSessions = generateWorkoutSessions()
        )

        private fun generateWorkoutSessions(): List<WorkoutSession> {
            return(0..4).map {
                WorkoutSession(
                    measurement = null,
                    cardios = listOf(DummyEntities.cardio.copy(type = Cardio.Walking.name)),
                    report = DummyEntities.dailyReport.copy(musclesTrained = listOf(getRandomMuscle()))
                )
            }
        }

        private fun getRandomMuscle(): Muscle = Muscle.entries[(0..Muscle.entries.size).random()]
    }



}