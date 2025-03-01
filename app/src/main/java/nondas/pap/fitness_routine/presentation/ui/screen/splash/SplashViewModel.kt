package com.example.fitness_routine.presentation.ui.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitness_routine.domain.interactor.settings.InitSettings

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val initSettings: InitSettings
): ViewModel() {


    init {
        viewModelScope.launch {
            initSettings.execute(Unit).fold(
                onSuccess = {},
                onFailure = {}
            )

        }
    }

}