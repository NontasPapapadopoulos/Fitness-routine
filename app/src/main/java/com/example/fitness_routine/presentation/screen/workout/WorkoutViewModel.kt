package com.example.fitness_routine.presentation.screen.workout

import com.example.fitness_routine.domain.entity.WorkoutWithSetsDomainEntity
import com.example.fitness_routine.presentation.BlocViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(

): BlocViewModel<WorkoutEvent, WorkoutState>() {



    override val _uiState: StateFlow<WorkoutState>
        get() = TODO("Not yet implemented")

}






sealed interface WorkoutEvent {

}



sealed interface WorkoutState {

    object Idle: WorkoutState

    data class Content(
        val workout: WorkoutWithSetsDomainEntity
    ): WorkoutState
}