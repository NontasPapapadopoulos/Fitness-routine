package com.example.fitness_routine.presentation.screen.cheat

import com.example.fitness_routine.presentation.BlocViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class CheatMealsViewModel @Inject constructor(

): BlocViewModel<CheatMealsEvent, CheatMealsState>() {


    override val _uiState: StateFlow<CheatMealsState>
        get() = TODO("Not yet implemented")


}





sealed interface CheatMealsEvent {

}


sealed interface CheatMealsState {
    object Idle : CheatMealsState
    data class Content(val xx: String) : CheatMealsState
}