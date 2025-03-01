package nondas.pap.fitness_routine.presentation.ui.screen.cheat


import nondas.pap.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import nondas.pap.fitness_routine.domain.entity.CheatMealDomainEntity
import nondas.pap.fitness_routine.domain.interactor.bodymeasurement.GetAllBodyMeasurements
import nondas.pap.fitness_routine.domain.interactor.cheat.GetAllCheatMeals
import nondas.pap.fitness_routine.presentation.ui.screen.MainDispatcherRule
import nondas.pap.fitness_routine.presentation.ui.screen.onEvents
import nondas.pap.fitness_routine.presentation.util.toTimeStamp
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class CheatMealsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var getCheatMeals: GetAllCheatMeals

    @Mock
    private lateinit var getAllBodyMeasurements: GetAllBodyMeasurements

    private lateinit var viewModel: CheatMealsViewModel

    @Before
    fun setUp() {
        whenever(getCheatMeals.execute(Unit)).thenReturn(flowOf(Result.success(meals)))
        whenever(getAllBodyMeasurements.execute(Unit)).thenReturn(flowOf(Result.success(bodyMeasurements)))
    }

    @Test
    fun onFlowStart_loadCheatMeals() = runTest {
        initViewModel()

        onEvents(viewModel) { collectedStates ->
            assertEquals(
                listOf(
                    CheatMealsState.Idle,
                    defaultContent,
                    defaultContent.copy(mealWithMeasurements)
                ),
                collectedStates
            )
        }
    }



    private fun initViewModel() {
        viewModel = CheatMealsViewModel(getCheatMeals, getAllBodyMeasurements)
    }



    companion object {
        val mealWithMeasurements = generateMealsWithMeasurements()

        val meals = (1..4).map {
            val localDate = LocalDate.of(2024, if (it < 5) 1 else 2, it)
            val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
            CheatMealDomainEntity(
                id = "",
                text = "burger",
                date = date
            )
        }
        val bodyMeasurements = (1..4).map {
            val localDate = LocalDate.of(2024, if (it < 5) 1 else 2, it)
            val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
            generateBodyMeasurements(date)
        }

        private fun generateMealsWithMeasurements(): List<MealWithMeasurement> {
            return (1..4).map {
                val localDate = LocalDate.of(2024, if (it < 5) 1 else 2, it)
                val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())

                MealWithMeasurement(
                    date = date,
                    meals = generateMeals(date),
                    measurement = generateBodyMeasurements(date)
                )
            }
        }

        private fun generateMeals(date: Date): List<CheatMealDomainEntity> {
            return (1..1).map {
                CheatMealDomainEntity(
                    id = "",
                    text = "burger",
                    date = date
                )
            }
        }

        private fun generateBodyMeasurements(date: Date): BodyMeasurementDomainEntity {
            return BodyMeasurementDomainEntity(
                id = "",
                date = date.toTimeStamp(),
                weight = "80.Kg",
                fat = "10",
                metabolicAge = "",
                visceralFat = "",
                bmr = "",
                tbw = "",
                bmi = "",
                muscleMass = ""
            )
        }
    }

    private val defaultContent = CheatMealsState.Content(
        mealWithMeasurements = listOf()
    )

}