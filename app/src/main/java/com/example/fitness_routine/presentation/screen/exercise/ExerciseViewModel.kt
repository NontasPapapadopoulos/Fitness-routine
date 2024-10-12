package com.example.fitness_routine.presentation.screen.exercise

import com.example.fitness_routine.presentation.BlocViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class ExerciseViewModel @Inject constructor(

): BlocViewModel<ExerciseEvent, ExerciseState>() {


    override val _uiState: StateFlow<ExerciseState>
        get() = TODO("Not yet implemented")











}







sealed interface ExerciseEvent {

}


sealed interface ExerciseState {

}