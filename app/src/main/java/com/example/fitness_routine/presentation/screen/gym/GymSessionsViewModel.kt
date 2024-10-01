package com.example.fitness_routine.presentation.screen.gym

import com.example.fitness_routine.presentation.BlocViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class GymSessionsViewModel @Inject constructor(

): BlocViewModel<GymSessionsEvent, GymSessionsState>() {


    override val _uiState: StateFlow<GymSessionsState>
        get() = TODO("Not yet implemented")


}





sealed interface GymSessionsEvent {

}


sealed interface GymSessionsState {
    object Idle : GymSessionsState
    data class Content(val xx: String) : GymSessionsState
}