package com.example.fitness_routine.presentation.ui.screen.cheat

import androidx.lifecycle.viewModelScope
import com.example.fitness_routine.domain.entity.CheatMealDomainEntity
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.interactor.cheat.GetAllCheatMeals
import com.example.fitness_routine.presentation.BlocViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class CheatMealsViewModel @Inject constructor(
    getCheatMeals: GetAllCheatMeals
): BlocViewModel<CheatMealsEvent, CheatMealsState>() {

    private val dailyReportsFlow = getCheatMeals.execute(Unit)
        .map { it.getOrThrow() }
        .catch { addError(it) }

    override val _uiState: StateFlow<CheatMealsState> = dailyReportsFlow.map {
        CheatMealsState.Content(
            meals = it
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = CheatMealsState.Idle
    )


}



sealed interface CheatMealsEvent {

}


sealed interface CheatMealsState {
    object Idle : CheatMealsState
    data class Content(val meals: List<CheatMealDomainEntity>) : CheatMealsState
}