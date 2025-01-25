package com.example.fitness_routine.presentation.ui.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitness_routine.domain.interactor.auth.GetSkipLogin
import com.example.fitness_routine.domain.interactor.settings.InitSettings

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val initSettings: InitSettings,
    private val getSkipLogin: GetSkipLogin
): ViewModel() {

    private val _navigationFlow = MutableSharedFlow<NavigationTarget>()
    val navigationFlow: SharedFlow<NavigationTarget> = _navigationFlow


    init {
        viewModelScope.launch {
            initSettings.execute(Unit).fold(
                onSuccess = {},
                onFailure = {}
            )
        }

        viewModelScope.launch {
            getSkipLogin.execute(Unit).fold(
                onSuccess = { navigate(it) },
                onFailure = {}
            )
        }
    }

    private suspend fun navigate(skipLogin: Boolean) {
        if (skipLogin)
            _navigationFlow.emit(NavigationTarget.Calendar)
        else
            _navigationFlow.emit(NavigationTarget.Login)
    }


    enum class NavigationTarget {
        Login,
        Calendar
    }

}