package com.example.fitness_routine.presentation.ui.screen.cheat

import androidx.lifecycle.viewModelScope
import com.example.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import com.example.fitness_routine.domain.entity.CheatMealDomainEntity
import com.example.fitness_routine.domain.interactor.bodymeasurement.GetAllBodyMeasurements
import com.example.fitness_routine.domain.interactor.cheat.GetAllCheatMeals
import com.example.fitness_routine.presentation.BlocViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class CheatMealsViewModel @Inject constructor(
    getCheatMeals: GetAllCheatMeals,
    getAllBodyMeasurements: GetAllBodyMeasurements
): BlocViewModel<CheatMealsEvent, CheatMealsState>() {

    private val cheatMealsFlow = getCheatMeals.execute(Unit)
        .map { it.getOrThrow() }
        .catch { addError(it) }


    private val measurementsFlow = getAllBodyMeasurements.execute(Unit)
        .map { it.getOrThrow() }
        .catch { addError(it) }


    override val _uiState: StateFlow<CheatMealsState> = combine(
        cheatMealsFlow.onStart { emit(listOf()) },
        measurementsFlow.onStart { emit(listOf()) }
    ) { meals, measurements ->

        val mealsByDate = meals.groupBy { it.date }
        val measurementsByDate = measurements.groupBy { Date(it.date) }

        val allDates = (mealsByDate.keys + measurementsByDate.keys).sorted()

        val mealWithMeasurements = allDates.map { date ->
            MealWithMeasurement(
                meals = mealsByDate[date],
                measurement = measurementsByDate[date]?.firstOrNull(),
                date = date
            )
        }

        CheatMealsState.Content(
            mealWithMeasurements
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = CheatMealsState.Idle
    )

}


sealed interface CheatMealsEvent {

}


data class MealWithMeasurement(
    val meals: List<CheatMealDomainEntity>?,
    val measurement: BodyMeasurementDomainEntity?,
    val date: Date
)

sealed interface CheatMealsState {
    object Idle : CheatMealsState
    data class Content(val mealWithMeasurements: List<MealWithMeasurement>) : CheatMealsState
}